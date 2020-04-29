/*
 *
 *   - Reveice, interpret and validate input.
 *   - Create and update views,
 *   - Query and modify models.
 *
 */

package seabattle;


public class BoardController {

    private BoardModel model;
    private BoardView view;

    public BoardController() {
        // Create the Model.
        model = new BoardModel();
        // Create the View.
        view = new BoardView(this, model);
    }

    // Receive event information from View.
    public void onMouseWasPressed(int x, int y) {
        // Tell model to do stuff
        System.out.println("Controller: received event from view.");
        model.setCell(y / view.getCellSize(), x / view.getCellSize());
    }

}
