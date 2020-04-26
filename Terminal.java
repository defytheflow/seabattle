public class Terminal {

    private static final String ESCAPE_SEQ = "\u001B[";
    private static final String WIDTH_ENV = "COLUMNS";
    private static final String HEIGHT_ENV = "LINES";

    public String getForegroundColorCode(String color) {
        switch (color) {
            case "reset":   return ESCAPE_SEQ + "0m";
            case "black":   return ESCAPE_SEQ + "30m";
            case "red":     return ESCAPE_SEQ + "31m";
            case "green":   return ESCAPE_SEQ + "32m";
            case "yellow":  return ESCAPE_SEQ + "33m";
            case "blue":    return ESCAPE_SEQ + "34m";
            case "magenta": return ESCAPE_SEQ + "35m";
            case "cyan":    return ESCAPE_SEQ + "36m";
            case "white":   return ESCAPE_SEQ + "37m";
            default:        return "";
        }
    }

    public String getBackgroundColorCode(String color) {
        switch (color) {
            case "reset":   return ESCAPE_SEQ + "0m";
            case "black":   return ESCAPE_SEQ + "40m";
            case "red":     return ESCAPE_SEQ + "41m";
            case "green":   return ESCAPE_SEQ + "42m";
            case "yellow":  return ESCAPE_SEQ + "43m";
            case "blue":    return ESCAPE_SEQ + "44m";
            case "magenta": return ESCAPE_SEQ + "45m";
            case "cyan":    return ESCAPE_SEQ + "46m";
            case "white":   return ESCAPE_SEQ + "47m";
            default:        return "";
        }
    }

    public int getWidth() {
        Integer width = null;
        try {
            width = Integer.parseInt(System.getenv(WIDTH_ENV));
        } catch (NumberFormatException e) {
            System.err.printf(
                "Error: unable to read value of $%s variable.", WIDTH_ENV
            );
            System.exit(1);
        }
        return width;
    }

    public int getHeight() {
        Integer height = null;
        try {
            height = Integer.parseInt(System.getenv(HEIGHT_ENV));
        } catch (NumberFormatException e) {
            System.err.printf(
                "Error: unable to read value of $%s variable.", HEIGHT_ENV
            );
            System.exit(1);
        }
        return height;
    }

}
