import java.util.Arrays;
import java.util.ArrayList;


public class Board {

    private static final char EMPTY_CELL = '0';
    private static final ArrayList<String> BOARD_LAYOUTS;
    private static final ArrayList<String> SHIP_COLORS;

    static {
        BOARD_LAYOUTS = new ArrayList<String>(
            Arrays.asList("left", "center", "right")
        );
        SHIP_COLORS = new ArrayList<String>(
            Arrays.asList(
                "black", "red", "green", "yellow",
                "blue", "magenta", "cyan", "white"
            )
        );
    }

    private int size;
    private char[][] buffer;
    private String layout;
    private String shipColor;
    private Terminal term;
    private String bgColor = "white";
    private String fgColor = "black";

    public Board(int size, String layout, String shipColor) {
        this.term = new Terminal();
        setSize(size);
        setLayout(layout);
        setShipColor(shipColor);
        setBuffer(size);
    }

    /* Check that board size does not exceed terminal height. */
    private void setSize(int size) throws IllegalArgumentException {
        if (size > this.term.getHeight()) {
            throw new IllegalArgumentException(
                "Error: size is greater than terminal height. " +
                "Must be <= " + term.getHeight() + "."
            );
        }
        this.size = size;
    }

    /* Check that layout is inside BOARD_LAYOUTS. */
    private void setLayout(String layout) throws IllegalArgumentException {
        if (!BOARD_LAYOUTS.contains(layout)) {
            throw new IllegalArgumentException(
                "Error: invalid layout value. " +
                "Valid values are " + BOARD_LAYOUTS.toString() + "."
            );
        }
        this.layout = layout;
    }

    /* Check that shipColor is inside SHIP_COLORS. */
    private void setShipColor(String shipColor) throws IllegalArgumentException {
        if (!SHIP_COLORS.contains(shipColor)) {
            throw new IllegalArgumentException(
                "Error: invalid shipColor value. " +
                "Valid values are " + SHIP_COLORS.toString() + "."
            );
        }
        this.shipColor = shipColor;
    }

    /* Initialize buffer with empty cells. */
    private void setBuffer(int size) {
        this.buffer = new char[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                this.buffer[i][j] = EMPTY_CELL;
            }
        }
    }

    /* Print board to stdout. */
    public void print() {
        int horizontalPadding = getHorizontalPadding();
        printHorizontalBorder(horizontalPadding);

        for (char[] row : this.buffer) {
            printTimes(" ", horizontalPadding);
            printLeftVerticalBorder(horizontalPadding);

            for (char col : row) {
                if (col != EMPTY_CELL) {
                    System.out.print(term.getForegroundColorCode(this.shipColor));
                }
                System.out.print(col);
                System.out.print(term.getForegroundColorCode("reset"));
                System.out.print(" ¦ ");
            }
            printRightVerticalBorder();
        }
        printHorizontalBorder(horizontalPadding);
    }

    /* Calculate horizontal padding based on terminal width on board layout. */
    private int getHorizontalPadding() {
        Integer horizontalPadding = null;

        switch (this.layout) {
            case "left":
                horizontalPadding = 0;
                break;
            case "center":
                horizontalPadding = (term.getWidth() - 4 * this.size) / 2;
                break;
            case "right":
                horizontalPadding = (term.getWidth() - 2 * this.size - 2);
                break;
        }

        return horizontalPadding;
    }

    private void printLeftVerticalBorder(int padding) {
        System.out.print(term.getBackgroundColorCode(this.bgColor));
        System.out.print(term.getForegroundColorCode(this.fgColor));
        System.out.print(" ");

        System.out.print(term.getForegroundColorCode("reset"));
        System.out.print(" ");
    }

    private void printRightVerticalBorder() {
        System.out.print(term.getBackgroundColorCode(this.bgColor));
        System.out.print(term.getForegroundColorCode(this.fgColor));
        System.out.println("\b\b \n");
        System.out.print(term.getForegroundColorCode("reset"));
    }

    private void printHorizontalBorder(int padding) {
        printTimes(" ", padding);
        System.out.print(term.getBackgroundColorCode(this.bgColor));
        System.out.print(term.getForegroundColorCode(this.fgColor));
        for (int i = 0; i < this.size * 4 + 2; ++i) {
            if (i % 2 == 0) {
                System.out.print(term.getBackgroundColorCode(this.bgColor));
                System.out.print(term.getForegroundColorCode(this.fgColor));
            }
            else {
                System.out.print(term.getForegroundColorCode("reset"));
            }
            System.out.print(" ");
        }
        System.out.println("\n");
    }

    /* Print 's' String 'n' times. */
    private void printTimes(String s, int n) {
        for (int i = 0; i < n; ++i)
            System.out.print(s);
    }

    public ArrayList<ArrayList<Integer>> getPossibleLocations(int rowNum, int shipSize) {
        ArrayList<ArrayList<Integer>> possibleLocations = new ArrayList<ArrayList<Integer>>();
        char[] row = this.buffer[rowNum];

        for (int i = 0; i < this.size; ++i) {

            if (row[i] != EMPTY_CELL)
                continue;

            ArrayList<Integer> possibleLocation = new ArrayList<Integer>();
            possibleLocation.add(i);

            boolean foundPossibleLocation = false;
            for (int j = i + 1; j < this.size; ++j) {

                // If we have enough adjacent empty cells to possition a ship.
                if (possibleLocation.size() == shipSize) {
                    foundPossibleLocation = true;
                    break;
                }

                // If we see an empty cell and it is next to our previous empty cell.
                if (row[j] == EMPTY_CELL &&
                    j - possibleLocation.get(possibleLocation.size() - 1) == 1) {
                    possibleLocation.add(j);
                }

            }

            if (foundPossibleLocation)
                possibleLocations.add(possibleLocation);
        }
        return possibleLocations;
    }

    public void addShip(Ship ship) {

        boolean pickedLocation = false;
        while (!pickedLocation) {
            int randomRow = (int) (Math.random() * this.size);
            ArrayList<ArrayList<Integer>> possibleLocations = getPossibleLocations(randomRow, ship.getSize());

            // System.out.printf("Randomrow for ship %c is %d\n", ship.getSkin(), randomRow);
            // System.out.println(Arrays.toString(possibleLocations.toArray()));

            if (possibleLocations.size() > 0) {
                int randomLocation = (int) (Math.random() * possibleLocations.size());
                for (int index: possibleLocations.get(randomLocation)) {
                    this.buffer[randomRow][index] = ship.getSkin();
                }
                pickedLocation = true;
            }

        }
    }

    // public boolean addShip(Ship ship) {
        // int randomRow = pickRandomRow(ship);
        // if (randomRow != -1) {
        //     // Position ship on that row
        //     positionShipInRow(randomRow, ship);
        //     return true;
        // }

        // int randomColumn = pickRandomColumn(ship);
        // if (randomColumn != -1) {
        //     // Position ship on that column
        //     positionShipInColumn(randomColumn, ship);
        //     return true;
        // }

        // return false;
    // }

    // private positionShipInRow(int rowNum, Ship ship) {

    // }

    // private int pickRandomColumn(Ship ship) {
    //     ArrayList<Integer> occupiedColumns = new ArrayList<Integer>();
    //     while (occupiedColumns.size() != this.size) {
    //         int randomColumn = ThreadLocalRandom.current().nextInt(0, size + 1);
    //         if (countColumnEmptyCells(randomColumn) < ship.getSize()) {
    //             occupiedColumns.add(randomColumn);
    //             continue;
    //         }
    //         return randomColumn;
    //     }
    //     return -1;
    // }

    // private int pickRandomRow(Ship ship) {
    //     ArrayList<Integer> occupiedRows = new ArrayList<Integer>();
    //     while (occupiedRows.size() != this.size) {
    //         int randomRow = ThreadLocalRandom.current().nextInt(0, size + 1);
    //         if (countRowEmptyCells(randomRow) < ship.getSize()) {
    //             occupiedRows.add(randomRow);
    //             continue;
    //         }
    //         return randomRow;
    //     }
    //     return -1;
    // }

    // private int countRowEmptyCells(int rowNum) {
    //     int emptyCells = 0;
    //     for (int i = 0; i < this.size; ++i) {
    //         if (this.buffer[rowNum][i] == EMPTY_CELL)
    //             ++emptyCells;
    //     }
    //     return emptyCells;
    // }

    // private int rowMaxEmptyAdjacentCells(int rowNum) {
    //     int emptyAdjacentCells = 0;

    // }

    // private int countColumnEmptyCells(int colNum) {
    //     int emptyCells = 0;
    //     for (int i = 0; i < this.size; ++i) {
    //         if (this.buffer[i][colNum] == EMPTY_CELL)
    //             ++emptyCells;
    //     }
    //     return emptyCells;
    // }

}
