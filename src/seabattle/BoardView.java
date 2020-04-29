/*
 * The Graphical User Interface.
 */

package seabattle;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


@SuppressWarnings("serial")
public class BoardView  extends JFrame {

    private static final int TITLE_HEIGHT;
    private static final int BORDER_WIDTH;
    private static final int WINDOW_WIDTH;
    private static final int WINDOW_HEIGHT;
    private static final String TITLE;

    static {
        TITLE_HEIGHT  = 26;
        BORDER_WIDTH  = 2;
        WINDOW_WIDTH  = 600 + BORDER_WIDTH * 2;
        WINDOW_HEIGHT = 600 + TITLE_HEIGHT;
        TITLE = "Sea Battle";
    }

    private BoardModel boardModel;
    private BoardPanel boardPanel;

    public BoardView(BoardModel boardModel) {
        this.boardModel = boardModel;
        initComponents();
    }

    private void initComponents() {
        /* Frame */
        setTitle(TITLE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);  // Center on the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* Panel */
        boardPanel = new BoardPanel();

        getContentPane().add(BorderLayout.CENTER, boardPanel);
    }

    class BoardPanel extends JPanel {

        private int boardSize;
        private int cellSize;

        public BoardPanel() {
            boardSize = boardModel.getSize();
            cellSize =  Math.min(WINDOW_WIDTH, WINDOW_HEIGHT) / boardSize;

            addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent e) {
                    System.out.printf("%d %d\n", e.getX(), e.getY());
                }

            });
        }

        @Override
        public void paintComponent(Graphics g) {
            for (int row = 0, y = 0; row < boardSize; ++row, y+=cellSize) {
                for (int col = 0, x = 0; col < boardSize; ++col, x+=cellSize) {
                    g.drawRect(x, y, cellSize, cellSize);
                }
            }
        }

    }

}


