package seabattle;


public class BoardController implements ViewListener, ModelListener {

    private BoardModel model;
    private BoardView view;

    public BoardController() {
        model = new BoardModel();
        model.addModelListener(this);

        view = new BoardView(model.getBoardSize());
        view.addViewListener(this);

        view.paint(model.getBoard());
    }

    @Override
    public void viewEventHappened(ViewEvent event) {
        System.out.printf("Controller: View event happened - ");
        switch (event) {
            case MOUSE_PRESS:
                System.out.printf("MOUSE_PRESS\n");
                int row = view.getMouseY() / view.getCellSize();
                int col = view.getMouseX() / view.getCellSize();
                model.setCell(row, col);
                break;
            default:
                break;
        }
    }

    @Override
    public void modelEventHappened(ModelEvent event) {
        System.out.printf("Controller: Model event happened - ");
        switch (event) {
            case UPDATE:
                System.out.printf("UPDATE\n");
                view.repaint();
                break;
            default:
                break;
        }
    }

}
