/*
 *   - Store data.
 *   - Provide data.
 *   - Update data.
 *   - Delete data.
 *
 */

package seabattle;

import java.util.Arrays;
import java.util.ArrayList;


public class BoardModel {

    private static final char EMPTY_CELL;
    private static final char OCCUPIED_CELL;
    private static final int BOARD_SIZE;

    static {
        EMPTY_CELL = '0';
        OCCUPIED_CELL = '1';
        BOARD_SIZE = 10;
    }

    private char[][] board;
    private int boardSize;
    private BoardView listener;

    public BoardModel() {
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

    public int getBoardSize() {
        return BOARD_SIZE;
    }

    public char[][] getBoard() {
        return board;
    }

    public void setCell(int row, int col) {
        // Check valid position.
        System.out.println("Model: received query from controller.");
        if (row > BOARD_SIZE || col > BOARD_SIZE || row < 0 || col < 0) {
            System.err.println("Error: invalid cell position");
            return;
        }

        if (board[row][col] != EMPTY_CELL) {
            System.err.println("Error: cell not empty!");
            return;
        }

        board[row][col] = OCCUPIED_CELL;
        listener.modelWasUpdated();
    }

    public boolean cellIsEmpty(int row, int col) {
        // Check valid position.
        return board[row][col] == EMPTY_CELL;
    }

    public void setListener(BoardView view) {
        listener = view;
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
