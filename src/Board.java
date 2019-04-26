import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private int[][] board;
    private PossibleValues[][] possibleValues;
    private boolean[][] defaultValues;
    private Point selected;
    
    public Board(int[][] initValues, boolean[][] defaultValues) {
        this.board = initValues;
        this.possibleValues = new PossibleValues[9][9];
        for(int x = 0; x < possibleValues.length; x++) {
            for(int y = 0; y < possibleValues[x].length; y++) {
                possibleValues[x][y] = new PossibleValues();
            }
        }
        this.defaultValues = defaultValues;
        for(int x = 0; x < defaultValues.length; x++) {
            for(int y = 0; y < defaultValues[x].length; y++) {
                if(defaultValues[x][y]) {
                    possibleValues[x][y].clearPossibilities();
                }
            }
        }
        this.selected = new Point(0, 0);
    }
    
    public int[][] getBoard() {
        return board;
    }
    
    public void drawBoard(Graphics2D g) {
        g.setFont(new Font("Dialog", Font.PLAIN, 40));
        for(int x = 0; x < board.length; x++) {
            for(int y = 0; y < board[x].length; y++) {
                if(board[y][x] != 0) {
                    if(defaultValues[y][x]) {
                        g.setColor(Color.BLACK);
                    } else {
                        g.setColor(Color.GRAY);
                    }
                    g.drawString(board[y][x]+"", x * SudokuSolver.WIDTH / 9 + 5, (y+1) * SudokuSolver.HEIGHT / 9 - 2);
                }
            }
        }
    }
    
    public void drawSelected(Graphics2D g) {
        g.setColor(Color.YELLOW);
        g.fillRect(selected.x * SudokuSolver.WIDTH / 9, selected.y * SudokuSolver.WIDTH / 9, SudokuSolver.WIDTH / 9, SudokuSolver.HEIGHT / 9);
    }
    
    public void updateSelected(int x, int y) {
        selected.setLocation(x / (SudokuSolver.WIDTH / 9), y / (SudokuSolver.HEIGHT / 9));
    }
    
    public void typeNumber(int num) {
        if(!defaultValues[selected.y][selected.x]) {
            board[selected.y][selected.x] = num;
            possibleValues[selected.y][selected.x].clearPossibilities();
        }
    }
    
    public void insertNumber(int num, int x, int y) {
        if(!defaultValues[y][x]) {
            board[y][x] = num;
            possibleValues[y][x].clearPossibilities();
        }
    }
    
    //Removes the options every box cannot be from the possibleValues array
    public void trimPossibleValues() {
        trimBoxes();
        trimVertical();
        trimHorizontal();
//        printPossibleBoard();
//        System.out.println("--------------------------------------------------------");
//        trimComplex();
//        printPossibleBoard();
    }
    
    private void trimBoxes() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                for(int x = 0; x < 3; x++) {
                    for(int y = 0; y < 3; y++) {
                        for(int k = 0; k < 3; k++) {
                            for(int l = 0; l < 3; l++) {
                                possibleValues[i*3+x][j*3+y].removePossibility(board[i*3+k][j*3+l]);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void trimVertical() {
        for(int x = 0; x < board.length; x++) {
            for(int y = 0; y < board[x].length; y++) {
                for(int z = 0; z < board[x].length; z++) {
                    possibleValues[y][x].removePossibility(board[z][x]);
                }
            }
        }
    }
    
    private void trimHorizontal() {
        for(int x = 0; x < board.length; x++) {
            for(int y = 0; y < board[x].length; y++) {
                for(int z = 0; z < board[x].length; z++) {
                    possibleValues[x][y].removePossibility(board[x][z]);
                }
            }
        }
    }
    
    private void trimComplex() {
        List<Point> points = new ArrayList<Point>();
        //Goes through each box
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                for(int num = 1; num <= 9; num++) {
                    points.clear();
                    //Add all instances of num in possible values to list
                    for(int i = 0; i < 3; i++) {
                        for(int j = 0; j < 3; j++) {
                            if(possibleValues[x*3+i][y*3+j].canBe(num)) {
                                points.add(new Point(x*3+i, y*3+j));
                            }
                        }
                    }
                    //Check all points are on the same axis (horizontal)
                    Point basePoint = new Point();
                    ArrayList<Integer> yPoints = new ArrayList<Integer>();
                    boolean aligned = false;
                    for(int i = 0; i < points.size(); i++) {
                        if(i == 0) {
                            aligned = true;
                            basePoint = points.get(i);
                        } else if(points.get(i).x != basePoint.x) {
                            aligned = false;
                        }
                        yPoints.add(points.get(i).y);
                    }
                    if(aligned) {
                        for(int i = 0; i < possibleValues.length; i++) {
                            if(!yPoints.contains(i)) {
                                possibleValues[basePoint.x][i].removePossibility(num);
                            }
                        }
                    }
                    //Check all points are on the same axis (vertical)
                    basePoint = new Point();
                    ArrayList<Integer> xPoints = new ArrayList<Integer>();
                    aligned = false;
                    for(int i = 0; i < points.size(); i++) {
                        if(i == 0) {
                            aligned = true;
                            basePoint = points.get(i);
                        } else if(points.get(i).y != basePoint.y) {
                            aligned = false;
                            break;
                        }
                        xPoints.add(points.get(i).x);
                    }
                    if(aligned) {
                        for(int i = 0; i < possibleValues.length; i++) {
                            if(!xPoints.contains(i)) {
                                System.out.print("x: " + i + " ");
                                System.out.print("y: " + basePoint.y + " ");
                                System.out.println(num);
                                possibleValues[i][basePoint.y].removePossibility(num);
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void insertSinglePossibilities() {
        insertOnlyOption();
        insertOnlyLine();
        insertOnlyBox();
    }
    
    private void insertOnlyOption() {
        for(int x = 0; x < board.length; x++) {
            for(int y = 0; y < board[x].length; y++) {
                if(possibleValues[x][y].isOnlyOption() != 0) {
                    insertNumber(possibleValues[x][y].isOnlyOption(), y, x);
                }
            }
        }
    }
    
    private void insertOnlyLine() {
        ValueCount[] onlyRow = new ValueCount[9];
        ValueCount[] onlyColumn = new ValueCount[9];
        for(int x = 0; x < board.length; x++) {
            for(int i = 0; i < onlyRow.length; i++) {
                onlyRow[i] = new ValueCount();
            }
            for(int i = 0; i < onlyColumn.length; i++) {
                onlyColumn[i] = new ValueCount();
            }
            for(int y = 0; y < board[x].length; y++) {
                if(board[x][y] != 0) {
                    onlyRow[board[x][y]-1].overCount();
                }
                for(int i = 1; i <= 9; i++) {
                    if(possibleValues[x][y].canBe(i)) {
                        onlyRow[i-1].update(x, y);
                    }
                }
                if(board[y][x] != 0) {
                    onlyColumn[board[y][x]-1].overCount();
                }
                for(int i = 1; i <= 9; i++) {
                    if(possibleValues[y][x].canBe(i)) {
                        onlyColumn[i-1].update(x, y);
                    }
                }
            }
            for(int i = 0; i < onlyRow.length; i++) {
                if(onlyRow[i].getCount() == 1) {
                    insertNumber(i+1, onlyRow[i].getY(), onlyRow[i].getX());
                }
                if(onlyColumn[i].getCount() == 1) {
                    insertNumber(i+1, onlyColumn[i].getX(), onlyColumn[i].getY());
                }
            }
        }
    }
    
    private void insertOnlyBox() {
        ValueCount[] onlyBox = new ValueCount[9];
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                for(int i = 0; i < onlyBox.length; i++) {
                    onlyBox[i] = new ValueCount();
                }
                
                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        if(board[x*3+i][y*3+j] != 0) {
                            onlyBox[board[x*3+i][y*3+j]-1].overCount();
                        }
                        for(int k = 1; k <= 9; k++) {
                            if(possibleValues[x*3+i][y*3+j].canBe(k)) {
                                onlyBox[k-1].update(x*3+i, y*3+j);
                            }
                        }
                    }
                }
                
                for(int i = 0; i < onlyBox.length; i++) {
                    if(onlyBox[i].getCount() == 1) {
                        insertNumber(i+1, onlyBox[i].getY(), onlyBox[i].getX());
                    }
                }
            }
        }
    }
    
    public void printBoard() {
        for(int x = 0; x < board.length; x++) {
            for(int y = 0; y < board[x].length; y++) {
                System.out.print(board[x][y]);
            }
            System.out.println();
        }
    }
    
    public void printPossibleBoard() {
        for(int x = 0; x < possibleValues.length; x++) {
            for(int y = 0; y < possibleValues[x].length; y++) {
                possibleValues[x][y].printPossibleValues();
                System.out.println();
            }
        }
    }
    
//    public boolean isSolved() {
//        Set<Integer> fullList = new HashSet<Integer>();
//        Set<Integer> testList;
//        
//        for(int i = 0; i < board.length; i++) {
//            for(int j = 0; j < board[i].length; j++) {
//                
//            }
//        }
//        
//    }
}

