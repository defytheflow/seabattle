/*
 * The graphical user interface of the application.
 */

package seabattle;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class BoardView  extends JFrame {

    private BoardModel boardModel;
    private BoardPanel boardPanel;

    public BoardView(BoardModel boardModel) {
        this.boardModel = boardModel;
        initComponents();
    }

    private void initComponents() {
        // Frame
        setSize(750, 750);
        setTitle("Sea Battle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel
        boardPanel = new BoardPanel();
        getContentPane().add(BorderLayout.CENTER, boardPanel);
    }

    class BoardPanel extends JPanel {

        public BoardPanel() {
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(750, 750));
        }

        @Override
        public void paintComponent(Graphics g) {
            int y = 0;
            int cellSize = 30;
            for (int row = 0; row < boardModel.getSize(); ++row) {
                int x = 0;
                for (int col = 0; col < boardModel.getSize(); ++col) {
                    g.drawRect(x, y, cellSize, cellSize);
                    x += cellSize;
                }
                y += cellSize;
            }
        }

    }

}


