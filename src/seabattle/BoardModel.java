/*
 *   - Store data.
 *   - Provide data.
 *   - Update data.
 *
 */

package seabattle;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;


public class BoardModel implements Model {

    private static final int  BOARD_SIZE;
    private static final char EMPTY_CELL, OCCUPIED_CELL;
    private static final HashMap<Integer, Integer> SHIP_COUNT;

    static {
        BOARD_SIZE = 10;
        EMPTY_CELL = '0';
        OCCUPIED_CELL = '1';
        SHIP_COUNT = createMap();
    }

    /* Static Methods */

    private static HashMap<Integer, Integer> createMap() {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(1, 4);
        map.put(2, 3);
        map.put(3, 2);
        map.put(4, 1);
        return map;
    }

    /* Instance Variables. */

    private ArrayList<ModelListener> modelListeners;
    private ArrayList<Ship> ships;
    private char[][] board;

    /* Construction/Initialization */

    public BoardModel() {
        modelListeners = new ArrayList<ModelListener>();
        initShips();
        initBoard();
    }

    private void initShips() {
        ships = new ArrayList<Ship>();
        for (int i = 0; i < SHIP_COUNT.get(1); ++i) {
            ships.add(new Ship(1));
        }
        for (int i = 0; i < SHIP_COUNT.get(2); ++i) {
            ships.add(new Ship(2));
        }
        for (int i = 0; i < SHIP_COUNT.get(3); ++i) {
            ships.add(new Ship(3));
        }
        for (int i = 0; i < SHIP_COUNT.get(4); ++i) {
            ships.add(new Ship(4));
        }
    }

    private void initBoard() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; ++i) {
            for (int j = 0; j < BOARD_SIZE; ++j) {
                board[i][j] = EMPTY_CELL;
            }
        }
        arrangeShips();
    }

    private void arrangeShips() {
        for (Ship ship : ships) {

            boolean arrangedShip = false;
            ArrayList<ArrayList<Integer>> shipLocations;

            while (!arrangedShip) {
                System.out.printf("BOARD_SIZE is %d\n", BOARD_SIZE);
                System.out.printf("getBoardSize is %d\n", getBoardSize());
                int randomRow = (int) Math.random() * getBoardSize();
                System.out.printf("For ship %c, picked row %d\n", ship.getCell(), randomRow);
                shipLocations = getShipLocations(randomRow, ship);

                // System.out.printf("Ship %c, Row %d\n", ship.getCell(), randomRow);
                // System.out.println(Arrays.toString(shipLocations.toArray()));

                if (shipLocations.size() == 0) {
                    System.exit(1);
                }

                if (shipLocations.size() > 0) {
                    int randomLocation = (int) Math.random() * shipLocations.size();
                    for (int col : shipLocations.get(randomLocation)) {
                        board[randomRow][col] = ship.getCell();
                    }
                    arrangedShip = true;
                }
            }
        }
    }

    private ArrayList<ArrayList<Integer>> getShipLocations(int row, Ship ship) {

        ArrayList<ArrayList<Integer>> shipLocations;
        shipLocations = new ArrayList<ArrayList<Integer>>();

        for (int col = 0; col < BOARD_SIZE; ++col) {

            if (!cellIsEmpty(row, col))
                continue;

            ArrayList<Integer> shipLocation = new ArrayList<Integer>();
            shipLocation.add(col);

            boolean foundShipLocation = false;

            for (int next_col = col + 1; next_col < BOARD_SIZE; ++next_col) {

                // If we have already found enough adjacent cells to arrange the ship.
                if (shipLocation.size() == ship.getSize()) {
                    foundShipLocation = true;
                    break;
                }

                // If we see an empty cell and it is adjacent to our previous empty cell.
                if (cellIsEmpty(row, next_col) &&
                    next_col - shipLocation.get(shipLocation.size() - 1) == 1) {
                        shipLocation.add(next_col);
                }
            }

            if (foundShipLocation) {
                shipLocations.add(shipLocation);
            }
        }

        return shipLocations;
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

    public char getCell(int row, int col) {

        if (!cellIsValid(row, col)) {
            System.err.println("Error: cell is not valid!");
            return '\0';
        }

        return board[row][col];
    }

    public boolean cellIsEmpty(int row, int col) {
        return (board[row][col] == EMPTY_CELL);
    }

    /* Private Methods */

    private boolean cellIsValid(int row, int col) {
        return (0 <= row && row < BOARD_SIZE) && (0 <= col && col < BOARD_SIZE);
    }

}
