import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SudokuSolver extends JPanel {
    private static final long serialVersionUID = 2474932357256719501L;
    public final static int WIDTH = 300;
    public final static int HEIGHT = 300;
    private static Board board;
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        SudokuSolver panel = new SudokuSolver();
        frame.getContentPane().add(panel);
        
        for(int i = 0; i < 1; i++) {
            board.trimPossibleValues();
            board.insertSinglePossibilities();
            
            panel.repaint();
        }
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public SudokuSolver() {
        try {
            Scanner boardScanner = new Scanner(new File("./src/BoardInput.txt"));
            int[][] inputBoard = new int[9][9];
            boolean[][] defaultValues = new boolean[9][9];
            for(int x = 0; x < 9; x++) {
                String line = boardScanner.nextLine();
                for(int y = 0; y < 9; y++) {
                    inputBoard[x][y] = Integer.parseInt(line.charAt(y)+"");
                    if(inputBoard[x][y] != 0) {
                        defaultValues[x][y] = true;
                    }
                }
            }
            boardScanner.close();
            board = new Board(inputBoard, defaultValues);
        } catch (FileNotFoundException e) {
            System.err.println(e);
        }
        
        addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                board.updateSelected(e.getX(), e.getY());
                repaint();
            }
            public void mouseEntered(MouseEvent e) {
            }
            public void mouseExited(MouseEvent e) {
            }
            public void mousePressed(MouseEvent e) {
            }
            public void mouseReleased(MouseEvent e) {
            }
        });
        
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_1:
                        board.typeNumber(1);
                        repaint();
                        break;
                    case KeyEvent.VK_2:
                        board.typeNumber(2);
                        repaint();
                        break;
                    case KeyEvent.VK_3:
                        board.typeNumber(3);
                        repaint();
                        break;
                    case KeyEvent.VK_4:
                        board.typeNumber(4);
                        repaint();
                        break;
                    case KeyEvent.VK_5:
                        board.typeNumber(5);
                        repaint();
                        break;
                    case KeyEvent.VK_6:
                        board.typeNumber(6);
                        repaint();
                        break;
                    case KeyEvent.VK_7:
                        board.typeNumber(7);
                        repaint();
                        break;
                    case KeyEvent.VK_8:
                        board.typeNumber(8);
                        repaint();
                        break;
                    case KeyEvent.VK_9:
                        board.typeNumber(9);
                        repaint();
                        break;
                    case KeyEvent.VK_0:
                        board.typeNumber(0);
                        repaint();
                        break;
                }
            }
        });
        
        setFocusable(true);
        requestFocus();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }
    
    public void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D) gr;
        
        board.drawSelected(g);
        board.drawBoard(g);
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
