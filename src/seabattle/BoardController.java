/*
 *
 *   - Reveice, interpret and validate input.
 *   - Create and update views,
 *   - Query and modify models.
 *
 */

package seabattle;


public class BoardController {

    private BoardModel boardModel;
    private BoardView boardView;

    public BoardController() {
        boardModel = new BoardModel(10);
        boardView = new BoardView(boardModel);
    }

    public void startApp() {
        boardView.setVisible(true);
    }

}
