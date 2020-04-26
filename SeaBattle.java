public class SeaBattle {

    private static int boardSize = 10;
    private static String boardLayout = "center";
    private static String shipColor = "green";

    public static void main(String[] args) {
        GUI gui = new GUI(750, 750, boardSize);
        gui.mainloop();

        // parseCmdLineArgs(args);
        // Board board = null;

        // try {
        //     board = new Board(boardSize, boardLayout, shipColor);
        // } catch (IllegalArgumentException e) {
        //     System.err.println(e.getMessage());
        //     System.exit(1);
        // }

        // for (int i = 0; i < 4; ++i)
        //     board.addShip(new Ship(1, '1'));

        // for (int i = 0; i < 3; ++i)
        //     board.addShip(new Ship(2, '2'));

        // for (int i = 0; i < 2; ++i)
        //     board.addShip(new Ship(3, '3'));

        // board.addShip(new Ship(4, '4'));

        // board.print();

    }

    /* Parse command line arguments. */
    private static void parseCmdLineArgs(String[] args) {
        for (int i = 0; i < args.length; ++i) {
            String arg = args[i];
            if (arg.startsWith("-")) {
                switch (arg) {

                    case "-c": case "--color":
                        shipColor = parseNextArg(args, i + 1, arg);
                        break;

                    case "-l": case "--layout":
                        boardLayout = parseNextArg(args, i + 1, arg);
                        break;

                    case "-s": case "--size":
                        boardSize = parseIntArg(
                            parseNextArg(args, i + 1, arg), "boardSize"
                        );
                        break;

                    default:
                        System.err.printf("Error: unknown option '%s'.\n", arg);
                        System.exit(1);
                        break;
                }
            }
        }
    }

    /* Try to parse the next argument. */
    private static String parseNextArg(String[] args, int index, String opt) {
        String arg = null;
        try {
            arg = args[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.printf("Error: option '%s' requires an argument.\n", opt);
            System.exit(1);
        }
        return arg;
    }

    /* Try to parse a String argument into an int. */
    private static int parseIntArg(String argStringValue, String argHelpName) {
        Integer argIntValue = null;
        try {
            argIntValue = Integer.parseInt(argStringValue);
        } catch (NumberFormatException e) {
            System.err.printf(
                "Error: %s must be a valid integer value.\n", argHelpName
            );
            System.exit(1);
        }
        return argIntValue;
    }

}
