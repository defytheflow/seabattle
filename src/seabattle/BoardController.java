package seabattle;


public class BoardController implements EventListener
{
    private BoardModel model;
    private BoardView view;

    public BoardController()
    {
        initModel();
        initView();
    }

    private void initModel()
    {
        int boardSize = 10;
        model = new BoardModel(boardSize);
    }

    private void initView()
    {
        char[][] board = model.getBoard();
        int width = 600 + 2 + 2;
        int height = 600 + 26;
        view = new BoardView(board, width, height, "Sea Battle");
        view.setEventListener(this);
    }

    @Override
    public void eventHappened(ViewEvent event)
    {
        switch (event) {
            case MOUSE_PRESS:
                processMousePress();
                break;
            default:
                break;
        }
    }

    private void processMousePress()
    {
        int row = view.getMouseY() / view.getCellSize();
        int col = view.getMouseX() / view.getCellSize();
        model.setCell(row, col);
        view.repaint(model.getBoard());
    }
}
