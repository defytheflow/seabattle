/*
 * The Graphical User Interface.
 */

package seabattle;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.HashMap;


@SuppressWarnings("serial")
public class BoardView  extends JFrame implements ModelListener {

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

    private BoardController controller;
    private BoardModel model;
    private BoardPanel panel;
    private int cellSize;

    /* Construction/Initialization */

    public BoardView(BoardController controller, BoardModel model) {
        initFrame();

        // Set controller and model references.
        this.controller = controller;
        this.model = model;

        // Calculate cellSize in pixels.
        cellSize =  Math.min(WIDTH, HEIGHT) / model.getBoardSize();

        // Initialize frame components.
        initComponents();

        // Draw frame.
        setVisible(true);
    }

    private void initFrame() {
        setTitle("Sea Battle");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        // Initialize Panel.
        panel = new BoardPanel();
        panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                controller.mousePressed(e.getX(), e.getY());
            }
        });
        // Add panel to Frame.
        getContentPane().add(BorderLayout.CENTER, panel);
    }


    /* ModelListener Interface */

    @Override
    public void modelUpdated() {
        repaint();
    }

    /* Public Methods */

    public int getCellSize() {
        return cellSize;
    }

    /* Inner Class */

    class BoardPanel extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            drawCells(g);
            drawCellBorders(g);
        }

        private void drawCells(Graphics g) {
            int y = 0;
            for (int row = 0; row < model.getBoardSize(); ++row) {
                int x = 0;
                for (int col = 0; col < model.getBoardSize(); ++col) {
                    g.setColor(getCellColor(row, col));
                    g.fillRect(x, y, cellSize, cellSize);
                    x += cellSize;
                }
                y += cellSize;
            }
        }

        private Color getCellColor(int row, int col) {
            return CELLS_COLORS.get(model.getCell(row, col));
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


