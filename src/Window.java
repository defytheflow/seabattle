import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class Window extends JFrame {

    private BoardPanel boardPanel;
    private InputPanel inputPanel;

    public Window(Dimension dim, String title) {
        setSize(dim.width, dim.height);
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        boardPanel = new BoardPanel(
            new Dimension(getWidth() * 8 / 10, getHeight() * 8 / 10),
            Color.WHITE,
            10
        );

        inputPanel = new InputPanel(
            new Dimension(getWidth(), getHeight() * 1 / 10),
            Color.WHITE,
            new Color(41, 41, 41)
        );

        getContentPane().add(BorderLayout.CENTER, boardPanel);
        getContentPane().add(BorderLayout.SOUTH, inputPanel);
    }

}


class BoardPanel extends JPanel {

    private int cellCount;
    private int cellSize;

    public BoardPanel(Dimension dim, Color bg, int cellCount) {
        setPreferredSize(dim);
        setBackground(bg);
        this.cellCount = cellCount;
        System.out.println(dim.height);
        this.cellSize = Math.min(dim.width, dim.height) / cellCount;
        System.out.println(cellSize);
    }

    @Override
    public void paintComponent(Graphics g) {
        int y = 0;

        for (int row = 0; row < cellCount; ++row) {
            int x = 0;
            for (int col = 0; col < cellCount; ++col) {
                g.drawRect(x, y, cellSize, cellSize);
                x += cellSize;
            }
            y += cellSize;
        }
    }

}


class InputPanel extends JPanel implements ActionListener {

    private JLabel posLabel;
    private JTextField posTextField;
    private JButton shootButton;

    public InputPanel(Dimension dim, Color fg, Color bg) {
        this.setBackground(bg);
        this.setPreferredSize(dim);
        initPosLabel("Position: ", fg);
        initPosTextField(20);
        initShootButton("Shoot", fg, bg);
    }

    private void initPosLabel(String text, Color fg) {
        posLabel = new JLabel();
        posLabel.setText(text);
        posLabel.setForeground(fg);
        this.add(posLabel);
    }

    private void initPosTextField(int cols) {
        posTextField = new JTextField();
        posTextField.setColumns(cols);
        posTextField.addActionListener(this);
        this.add(posTextField);
    }

    private void initShootButton(String text, Color fg, Color bg) {
        shootButton = new JButton();
        shootButton.setText(text);
        shootButton.setForeground(fg);
        shootButton.setBackground(bg);
        shootButton.setFocusPainted(false);
        shootButton.addActionListener(this);
        this.add(shootButton);
    }

    public void actionPerformed(ActionEvent event) {
        System.out.println("Player shot: " + posTextField.getText());
        posTextField.setText("");
    }

}
