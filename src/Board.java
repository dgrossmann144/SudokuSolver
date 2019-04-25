import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

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
        
        trimComplexVertical();
    }
    
    private void trimComplexVertical() {
        
    }
    
    private void trimHorizontal() {
        for(int x = 0; x < board.length; x++) {
            for(int y = 0; y < board[x].length; y++) {
                for(int z = 0; z < board[x].length; z++) {
                    possibleValues[x][y].removePossibility(board[x][z]);
                }
            }
        }
        
        trimComplexHorizontal();
    }
    
    private void trimComplexHorizontal() {
        
    }
    
    public void insertSinglePossibilities() {
        for(int x = 0; x < board.length; x++) {
            for(int y = 0; y < board[x].length; y++) {
                if(possibleValues[x][y].isOnlyOption() != 0) {
                    insertNumber(possibleValues[x][y].isOnlyOption(), y, x);
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
//        
//    }
}
