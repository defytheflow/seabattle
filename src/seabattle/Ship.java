package seabattle;


public class Ship {

    private static final int SHIP_MIN_SIZE;
    private static final int SHIP_MAX_SIZE;

    static {
        SHIP_MIN_SIZE = 1;
        SHIP_MAX_SIZE = 4;
    }

    private int size;
    private char cell;

    public Ship(int size) {
        if (!sizeIsValid(size)) {
            throw new IllegalArgumentException("Error: invalid ship size.");
        }
        this.size = size;
        this.cell = (char) (size + (int) '0');
    }

    public int getSize() {
        return size;
    }

    public char getCell() {
        return cell;
    }

    private boolean sizeIsValid(int size) {
        return (SHIP_MIN_SIZE <= size && size <= SHIP_MAX_SIZE);
    }

}
