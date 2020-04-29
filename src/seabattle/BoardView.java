/*
 * The Graphical User Interface.
 */

package seabattle;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


@SuppressWarnings("serial")
public class BoardView  extends JFrame {

    private static final int WIDTH;
    private static final int HEIGHT;

    static {
        WIDTH = 600 + 2 + 2;
        HEIGHT = 600 + 26;
    }

    private BoardController controller;
    private BoardModel model;
    private BoardPanel panel;
    private int cellSize;

    public BoardView(BoardController controller, BoardModel model) {
        // Set Frame properties.
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set controller and model references.
        this.controller = controller;
        this.model = model;
        model.setListener(this);

        // Calculate cellSize in pixels
        cellSize =  Math.min(WIDTH, HEIGHT) / model.getBoardSize();

        // Initialize frame components.
        initComponents();

        // Draw frame.
        setVisible(true);
    }

    private void initComponents() {
        // Initialize Panel.
        panel = new BoardPanel();
        panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                System.out.println("\nView: generated event.");
                // Event information is passed to controller.
                controller.onMouseWasPressed(e.getX(), e.getY());
            }
        });
        // Add panel to Frame.
        getContentPane().add(BorderLayout.CENTER, panel);
    }

    public int getCellSize() {
        return cellSize;
    }

    public void modelWasUpdated() {
        System.out.println("View: updated model presentation.");
        repaint();
    }

    class BoardPanel extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            // Draw rectangles.
            int boardSize = model.getBoardSize();
            for (int row = 0, y = 0; row < boardSize; ++row, y+=cellSize) {
                for (int col = 0, x = 0; col < boardSize; ++col, x+=cellSize) {
                    if (model.cellIsEmpty(row, col)) {
                        g.setColor(Color.WHITE);
                    } else {
                        g.setColor(Color.GREEN);
                    }
                    g.fillRect(x, y, cellSize, cellSize);
                }
            }

            g.setColor(Color.BLACK);

            // Draw vertical borders.
            for (int x = cellSize; x < getWidth(); x += cellSize) {
                g.drawLine(x, 0, x, getHeight());
            }

            // Draw horizontal borders
            for (int y = cellSize; y < getHeight(); y += cellSize) {
                g.drawLine(0, y, getWidth(), y);
            }

            // for (int i = 10; i <= 800; i += 10) {
            //     g.drawLine(i, 10, i, 510);
            // }

            // for (int i = 10; i <= 500; i += 10) {
            //     g.drawLine(10, i, 810, i);
            // }
        }

    }

}


