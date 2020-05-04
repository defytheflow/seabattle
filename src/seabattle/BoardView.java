package seabattle;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.ArrayList;
import java.util.HashMap;


@SuppressWarnings("serial")
public class BoardView extends JFrame
{
    private EventListener listener;

    private BoardPanel panel;

    private int cellSize;

    private int mouseX;
    private int mouseY;

    public BoardView(char[][] board, int width, int height, String title)
    {
        cellSize = Math.min(width, height) / board.length;

        panel = new BoardPanel(board);
        panel.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent event)
            {
                mouseX = event.getX();
                mouseY = event.getY();
                notifyEventListener(ViewEvent.MOUSE_PRESS);
            }
        });

        initFrame(width, height, title);
        getContentPane().add(BorderLayout.CENTER, panel);
    }

    private void initFrame(int width, int height, String title)
    {
        setTitle(title);
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void setEventListener(EventListener listener)
    {
        this.listener = listener;
    }

    public void notifyEventListener(ViewEvent event)
    {
        listener.eventHappened(event);
    }

    public int getCellSize()
    {
        return cellSize;
    }

    public int getMouseX()
    {
        return mouseX;
    }

    public int getMouseY()
    {
        return mouseY;
    }

    public void repaint(char[][] board)
    {
        panel.setBoard(board);
        repaint();
    }

    class BoardPanel extends JPanel
    {
        private char[][] board;
        private HashMap<Character, Color> colorMap;

        public BoardPanel(char[][] board)
        {
            this.board = board;
            initColorMap();
        }

        private void initColorMap()
        {
            colorMap = new HashMap<Character, Color>();
            colorMap.put('0', Color.WHITE);
            colorMap.put('1', Color.BLUE);
            colorMap.put('2', Color.RED);
            colorMap.put('3', Color.GREEN);
            colorMap.put('4', Color.MAGENTA);
        }

        @Override
        public void paintComponent(Graphics g)
        {
            drawCells(g);
            drawCellBorders(g);
        }

        public void setBoard(char[][] board)
        {
            this.board = board;
        }

        private void drawCells(Graphics g)
        {
            int y = 0;
            for (int row = 0; row < board.length; ++row) {
                int x = 0;
                for (int col = 0; col < board.length; ++col) {
                    g.setColor(colorMap.get(board[row][col]));
                    g.fillRect(x, y, cellSize, cellSize);
                    x += cellSize;
                }
                y += cellSize;
            }
        }

        private void drawCellBorders(Graphics g)
        {
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
