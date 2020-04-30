/*
 *   - Store data.
 *   - Provide data.
 *   - Update data.
 *
 */

package seabattle;

import java.util.ArrayList;


public class BoardModel implements Model {

    private static final char EMPTY_CELL;
    private static final char OCCUPIED_CELL;
    private static final int  BOARD_SIZE;

    static {
        EMPTY_CELL    = '0';
        OCCUPIED_CELL = '1';
        BOARD_SIZE    = 10;
    }

    private ArrayList<ModelListener> modelListeners;
    private char[][] board;

    /* Construction/Initialization */

    public BoardModel() {
        modelListeners = new ArrayList<ModelListener>();
        initBoard();
    }

    private void initBoard() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; ++i) {
            for (int j = 0; j < BOARD_SIZE; ++j) {
                board[i][j] = EMPTY_CELL;
            }
        }
    }

    /* Model Interface */

    @Override
    public void addModelListener(ModelListener ml) {
        if (!modelListeners.contains(ml)) {
            modelListeners.add(ml);
        }
    }

    @Override
    public void removeModelListener(ModelListener ml) {
        modelListeners.remove(ml);
    }

    @Override
    public void update() {
        for (ModelListener ml : modelListeners) {
            ml.modelUpdated();
        }
    }

    /* Public Methods */

    public int getBoardSize() {
        return BOARD_SIZE;
    }

    public void setCell(int row, int col) {

        if (!cellIsValid(row, col)) {
            System.err.println("Error: cell is not valid!");
            return;
        }

        if (!cellIsEmpty(row, col)) {
            System.err.println("Error: cell is not empty!");
            return;
        }

        board[row][col] = OCCUPIED_CELL;
        update();
    }

    public boolean cellIsEmpty(int row, int col) {
        return (board[row][col] == EMPTY_CELL);
    }

    /* Private Methods */

    private boolean cellIsValid(int row, int col) {
        return (0 <= row && row < BOARD_SIZE) && (0 <= col && col < BOARD_SIZE);
    }

}

// public class Board {

//     public ArrayList<ArrayList<Integer>> getPossibleLocations(int rowNum, int shipSize) {
//         ArrayList<ArrayList<Integer>> possibleLocations = new ArrayList<ArrayList<Integer>>();
//         char[] row = this.buffer[rowNum];

//         for (int i = 0; i < this.size; ++i) {

//             if (row[i] != EMPTY_CELL)
//                 continue;

//             ArrayList<Integer> possibleLocation = new ArrayList<Integer>();
//             possibleLocation.add(i);

//             boolean foundPossibleLocation = false;
//             for (int j = i + 1; j < this.size; ++j) {

//                 // If we have enough adjacent empty cells to possition a ship.
//                 if (possibleLocation.size() == shipSize) {
//                     foundPossibleLocation = true;
//                     break;
//                 }

//                 // If we see an empty cell and it is next to our previous empty cell.
//                 if (row[j] == EMPTY_CELL &&
//                     j - possibleLocation.get(possibleLocation.size() - 1) == 1) {
//                     possibleLocation.add(j);
//                 }

//             }

//             if (foundPossibleLocation)
//                 possibleLocations.add(possibleLocation);
//         }
//         return possibleLocations;
//     }

//     public void addShip(Ship ship) {

//         boolean pickedLocation = false;
//         while (!pickedLocation) {
//             int randomRow = (int) (Math.random() * this.size);
//             ArrayList<ArrayList<Integer>> possibleLocations = getPossibleLocations(randomRow, ship.getSize());

//             // System.out.printf("Randomrow for ship %c is %d\n", ship.getSkin(), randomRow);
//             // System.out.println(Arrays.toString(possibleLocations.toArray()));

//             if (possibleLocations.size() > 0) {
//                 int randomLocation = (int) (Math.random() * possibleLocations.size());
//                 for (int index: possibleLocations.get(randomLocation)) {
//                     this.buffer[randomRow][index] = ship.getSkin();
//                 }
//                 pickedLocation = true;
//             }

//         }
//     }

// }
