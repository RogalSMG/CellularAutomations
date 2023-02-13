import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Convey extends Automat {

    /**
     * Standard constructor where default all cells are turn off, they can be turned on by hardCoding
     */
    public Convey() {
        grid = new GridCanvas(30, 25, 20);
        grid.randomTurning01();
    }

    /**
     * Convey constructor allow to create :
     * <p>
     * O - Alive cells <p>
     * . - dead cells
     * </p>
     * handle .cells and .rle extensions. Encode rle and map to .cells format
     *
     * @param path   of file
     * @param margin amount of cell around given pattern
     */
    public Convey(String path, int margin) {
        if (path.endsWith("rle")) {
            rleConvey(path, margin);
        } else {
            cellsConvey(path, margin);
        }
    }

    public static void main(String[] args) {
        String title = "Game Of Life";
        Convey game = new Convey();
        //Convey game = new Convey("E:\\Developer Start\\Java Projekty\\GameOfLife\\src\\patterns\\testing.rle", 1);
        game.grid.setGoThroughBorder(false);
        game.run(title, 6);
    }

    /**
     * Helper
     * Method implements rules where cell get alive and when died.
     * -Alive cell having more than 3 neighbourhoods die because of crowding
     * -Alive cell having lesser than 2 neighbourhood die because of solitude
     * -Dead cell having exactly 3 neighbourhoods revive
     *
     * @param cell  cell to check
     * @param count number of neighbourhoods
     */
    private static void updateCell(Cell cell, int count) {
        if (cell.isOn()) {
            if (count > 3 || count < 2) {
                cell.turnOff();
            }
        } else if (count == 3) {
            cell.turnOn();
        }
    }

    /**
     * Helper method which take string and return int value made of all digits inside given string
     *
     * @param str given string
     * @return int value made of {@code str} parameter
     */
    private int leftOnlyDigits(String str) {
        char[] arr = str.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char ch : arr) {
            if (Character.isDigit(ch)) {
                builder.append(ch);
            }
        }
        return Integer.parseInt(builder.toString());
    }

    /**
     * Method read .rle extension and transform it to .cells
     *
     * @param path   of file
     * @param margin additional cells around pattern
     */
    private void rleConvey(String path, int margin) {
        Scanner scan = loadFile(path); // load file to scanner

        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> prefixList = new ArrayList<>();

        // read text file to array
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if (!line.startsWith("#")) {
                list.add(line);
            } else prefixList.add(line);
        }

        // set dimensions, dimensions are provided in first line of file
        String line = list.get(0);
        String[] firstLine = line.split(" ");
        int width = 0;
        int height = 0;

        for (int j = 0; j < firstLine.length; ) {
            if (firstLine[j].equals("x")) {
                width = leftOnlyDigits(firstLine[j + 2]);
                j = 3;
            }
            if (firstLine[j].equals("y")) {
                height = leftOnlyDigits(firstLine[j + 2]);
                break;
            }
        }

        // get all lines in one string
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < list.size(); i++) {
            builder.append(list.get(i));
        }

        // encoding and making list
        ArrayList<StringBuilder> encoding = new ArrayList<>();
        encoding.add(new StringBuilder());
        int lineNumber = 0;
        for (int i = 0; i < builder.length(); i++) {
            char ch = builder.charAt(i);
            int amount = 1;
            switch (ch) {
                case 'b' -> {
                    if (i != 0 && Character.isDigit(builder.charAt(i - 1))) {
                        amount = isNumber(builder, i - 1);
                    }
                    for (int j = 0; j < amount; j++) {
                        encoding.get(lineNumber).append(".");
                    }
                }
                case 'o' -> {
                    if (i != 0 && Character.isDigit(builder.charAt(i - 1))) {
                        amount = isNumber(builder, i - 1);
                    }
                    for (int j = 0; j < amount; j++) {
                        encoding.get(lineNumber).append("O");
                    }
                }
                case '!' -> {
                    break; // prevent from reading after exclamation mark
                }
                case '$' -> {
                    lineNumber++;
                    encoding.add(new StringBuilder());
                }
            }
        }

        ArrayList<String> encoded = new ArrayList<>();
        for (StringBuilder sb : encoding) {
            encoded.add(sb.toString());
        }

        grid = new GridCanvas(height + margin * 2, width + margin * 2, 20);
        turnOnCells(encoded, margin);
    }

    /**
     * Helper method in decoding RLE
     *
     * @param code string of code
     * @param i    index of number which precede last funded o or b
     * @return integer value of number
     */
    protected int isNumber(StringBuilder code, int i) {
        int count = 0;
        int temp = i;
        while (temp != 0 && Character.isDigit(code.charAt(temp - 1))) {
            count++;
            temp--;
        }
        return Integer.parseInt(code.substring(i - count, i + 1));
    }

    /**
     * Method read coordination's of cells from .cells extension files
     *
     * @param path   of file
     * @param margin given margin
     */
    private void cellsConvey(String path, int margin) {
        Scanner scan = loadFile(path);

        // read text file to array
        ArrayList<String> list = new ArrayList<>();
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if (!line.startsWith("!")) {
                list.add(line);
            }
        }

        // set dimension of pattern
        int width = 0;
        int height = list.size();

        for (String str : list) {
            if (str.length() > width) {
                width = str.length();
            }
        }
        grid = new GridCanvas(height + margin * 2, width + margin * 2, 20);

        turnOnCells(list, margin);
    }

    /**
     * Method which turn on specified cells by pattern
     * .O..OO
     * O...OO
     * where big O are turning on and dots . still off
     *
     * @param list   {@code ArrayList<String>}
     * @param margin additional cells around pattern
     */
    private void turnOnCells(ArrayList<String> list, int margin) {
        // turning on specified cells
        for (int i = 0; i < list.size(); i++) {
            String line = list.get(i);
            for (int j = 0; j < line.length(); j++) {
                char ch = line.charAt(j);
                if (ch == 'O') {
                    grid.getCell(i + margin, j + margin).turnOn();
                }
            }
        }
    }

    /**
     * Getter
     *
     * @return this.grid
     */
    public GridCanvas getGrid() {
        return grid;
    }

    /**
     * Load text file
     */
    private Scanner loadFile(String path) {
        File file = new File(path);
        Scanner scan;
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return scan;
    }

    /**
     * Method simulate one round of Game of life
     */
    public void update() {
        int[][] counts = countNeighbors();
        updateGrid(counts);
    }

    /**
     * Method go through whole {@code Cell[][]} grid and update status of each cell
     *
     * @param counts counted neighbourhoods of each {@code Cell}
     */
    private void updateGrid(int[][] counts) {
        int rows = grid.numRows();
        int cols = grid.numColumns();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = grid.getCell(r, c);
                updateCell(cell, counts[r][c]);
            }
        }
    }

    /**
     * Helper
     * Method count neighbourhoods of all cells of {@code this.grid.grid Cell[][] }field
     *
     * @return number of neighbourhoods of each cell
     */
    private int[][] countNeighbors() {
        int rows = grid.numRows();
        int cols = grid.numColumns();

        int[][] counts = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                counts[i][j] = grid.countNeight(i, j);
            }
        }
        return counts;
    }
}
