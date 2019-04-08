import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SudokuSolver extends JPanel {
    private static final long serialVersionUID = 2474932357256719501L;
    private final int WIDTH = 300;
    private final int HEIGHT = 300;
    private Board board;
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        SudokuSolver panel = new SudokuSolver();
        frame.getContentPane().add(panel);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public SudokuSolver() {
        board = new Board(new int[9][9]);
        
        setFocusable(true);
        requestFocus();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }
    
    public void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D) gr;
        
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(3));
        g.drawLine(WIDTH/3, 0, WIDTH/3, HEIGHT);
        g.drawLine(WIDTH*2/3, 0, WIDTH*2/3, HEIGHT);
        g.drawLine(0, HEIGHT/3, WIDTH, HEIGHT/3);
        g.drawLine(0, HEIGHT*2/3, WIDTH, HEIGHT*2/3);
        g.setStroke(new BasicStroke(1));
        g.drawLine(WIDTH/9, 0, WIDTH/9, HEIGHT);
        g.drawLine(WIDTH*2/9, 0, WIDTH*2/9, HEIGHT);
        g.drawLine(WIDTH*4/9, 0, WIDTH*4/9, HEIGHT);
        g.drawLine(WIDTH*5/9, 0, WIDTH*5/9, HEIGHT);
        g.drawLine(WIDTH*7/9, 0, WIDTH*7/9, HEIGHT);
        g.drawLine(WIDTH*8/9, 0, WIDTH*8/9, HEIGHT);
        g.drawLine(0, HEIGHT/9, WIDTH, HEIGHT/9);
        g.drawLine(0, HEIGHT*2/9, WIDTH, HEIGHT*2/9);
        g.drawLine(0, HEIGHT*4/9, WIDTH, HEIGHT*4/9);
        g.drawLine(0, HEIGHT*5/9, WIDTH, HEIGHT*5/9);
        g.drawLine(0, HEIGHT*7/9, WIDTH, HEIGHT*7/9);
        g.drawLine(0, HEIGHT*8/9, WIDTH, HEIGHT*8/9);
    }
}
