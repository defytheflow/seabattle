package seabattle;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;


@SuppressWarnings("serial")
public class BoardView extends JFrame implements View {

    private static final int WIDTH;
    private static final int HEIGHT;
    private static final HashMap<Character, Color> CELLS_COLORS;

    static {
        WIDTH = 600 + 2 + 2;
        HEIGHT = 600 + 26;
        CELLS_COLORS = createMap();
    }

    /* Static Methods */

    private static HashMap<Character, Color> createMap() {
        HashMap<Character, Color> map = new HashMap<Character, Color>();
        map.put('0', Color.WHITE);
        map.put('1', Color.BLUE);
        map.put('2', Color.RED);
        map.put('3', Color.GREEN);
        map.put('4', Color.MAGENTA);
        return map;
    }

    private ArrayList<ViewListener> viewListeners;
    private BoardPanel panel;

    private int cellSize;
    private int mouseX;
    private int mouseY;

    public BoardView(int boardSize) {
        viewListeners = new ArrayList<ViewListener>();
        initFrame();
        cellSize = Math.min(WIDTH, HEIGHT) / boardSize;
        initComponents();
    }

    private void initFrame() {
        setTitle("Sea Battle");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        panel = new BoardPanel();
        panel.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                update(ViewEvent.MOUSE_PRESS);
            }

        });
        getContentPane().add(BorderLayout.CENTER, panel);
    }

    @Override
    public void addViewListener(ViewListener vl) {
        if (!viewListeners.contains(vl)) {
            viewListeners.add(vl);
        }
    }

    @Override
    public void removeViewListener(ViewListener vl) {
        viewListeners.remove(vl);
    }

    @Override
    public void update(ViewEvent event) {
        for (ViewListener vl : viewListeners) {
            vl.viewEventHappened(event);
        }
    }

    public int getCellSize() {
        return cellSize;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public void paint(char[][] board) {
        panel.setBoard(board);
        setVisible(true);
    }

    class BoardPanel extends JPanel {

        private char[][] board;

        @Override
        public void paintComponent(Graphics g) {
            drawCells(g);
            drawCellBorders(g);
        }

        public void setBoard(char[][] board) {
            this.board = board;
        }

        private void drawCells(Graphics g) {
            int y = 0;
            for (int row = 0; row < board.length; ++row) {
                int x = 0;
                for (int col = 0; col < board.length; ++col) {
                    g.setColor(getCellColor(row, col));
                    g.fillRect(x, y, cellSize, cellSize);
                    x += cellSize;
                }
                y += cellSize;
            }
        }

        private Color getCellColor(int row, int col) {
            return CELLS_COLORS.get(board[row][col]);
        }

        private void drawCellBorders(Graphics g) {
            g.setColor(Color.BLACK);
            // Vertical.
            for (int x = cellSize; x < getWidth(); x += cellSize) {
                g.drawLine(x, 0, x, getHeight());
            }
            // Horizontal.
            for (int y = cellSize; y < getHeight(); y += cellSize) {
                g.drawLine(0, y, getWidth(), y);
            }
        }

    }

}


