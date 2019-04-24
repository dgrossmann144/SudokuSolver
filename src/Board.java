import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

public class Board {
    private int[][] board;
    private boolean[][] defaultValue;
    private Point selected = new Point(0, 0);
    
    public Board(int[][] initValues, boolean[][] defaultValue) {
        this.board = initValues;
        this.defaultValue = defaultValue;
    }
    
    public int[][] getBoard() {
        return board;
    }
    
    public void drawBoard(Graphics2D g) {
        g.setFont(new Font("Dialog", Font.PLAIN, 40));
        for(int x = 0; x < board.length; x++) {
            for(int y = 0; y < board[x].length; y++) {
                if(board[y][x] != 0) {
                    if(defaultValue[y][x]) {
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
        if(!defaultValue[selected.y][selected.x]) {
            board[selected.y][selected.x] = num;
        }
    }
}
