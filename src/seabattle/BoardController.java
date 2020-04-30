/*
 *
 *   - Reveice, interpret and validate input.
 *   - Create and update views,
 *   - Query and modify models.
 *
 */

package seabattle;


public class BoardController implements ViewListener {

    private BoardModel model;
    private BoardView view;

    public BoardController() {
        // Create the Model.
        model = new BoardModel();
        // Create the View.
        view = new BoardView(this, model);

        model.addModelListener(view);
    }

    @Override
    public void mousePressed(int x, int y) {
        // Tell model to do stuff
        System.out.println("Controller: received event from view.");
        model.setCell(y / view.getCellSize(), x / view.getCellSize());
    }

}
