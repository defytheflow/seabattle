import java.util.Arrays;


public class Ship {

    private static final int MIN_SIZE = 1;
    private static final int MAX_SIZE = 4;

    private int size;
    private char skin;
    private int[] pos;

    public Ship(int size, char skin) {
        setSize(size);
        this.skin = skin;
    }

    private void setSize(int size) throws IllegalArgumentException {
        if (size < MIN_SIZE || size > MAX_SIZE) {
            throw new IllegalArgumentException(
                "Error: ship size must be in range " + MIN_SIZE + ".." + MAX_SIZE + "."
            );
        }
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }

    public char getSkin() {
        return this.skin;
    }

}
