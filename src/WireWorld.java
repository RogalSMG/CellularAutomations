import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class WireWorld extends Automat {
    public static Color[] COLORS = {Color.black, Color.BLUE, Color.RED, Color.YELLOW};

    /**
     * Standard constructor
     */
    public WireWorld() {
        Cell.setCOLORS(COLORS);
        grid = new GridCanvas(20, 20, 20);
        grid.getCell(4, 4).setState(3);
        grid.getCell(4, 5).setState(3);
        grid.getCell(4, 6).setState(2);
        grid.getCell(5, 7).setState(1);
        grid.getCell(6, 6).setState(3);
        grid.getCell(6, 5).setState(3);
        grid.getCell(6, 4).setState(3);
        grid.getCell(5, 3).setState(3);
        grid.getCell(4, 4).setState(3);
    }

    /**
     * Constructor where size of grid are hardCoded
     *
     * @param rows number of rows
     * @param cols number of cols
     */
    public WireWorld(int rows, int cols) {
        Cell.setCOLORS(COLORS);
        grid = new GridCanvas(rows, cols, 20);
    }

    /**
     * Create and run simulation
     *
     * @param args command prompt argument
     */
    public static void main(String[] args) {
        WireWorld world = new WireWorld(20, 30);
        world.setStates(world.readWWP("E:\\Developer Start\\Java Projekty\\GameOfLife\\src\\wireWorldPatterns\\XOR_Gate.wwp"));
        world.run("Wire World", 4);
    }

    /**
     * Method simulate one electron move.
     */
    @Override
    public void update() {
        int[][] electronsHist = grid.countNeighbors(1);
        cellUpdate(electronsHist);
    }

    /**
     * Method implement rules for wire world cell automation. Rules:
     * - electron head (1) -> electron tail (2)
     * - electron tail (2) -> electron head (1)
     * - conductor (3) -> electron head (1) only if surrounded exact by one or two other electrons
     *
     * @param electronHist two dimension int array included amount of electron heads cells {@code  cell.getState == 2} around each {@code cell} of grid
     */
    private void cellUpdate(int[][] electronHist) {
        for (int y = 0; y < grid.numRows(); y++) {
            for (int x = 0; x < grid.numColumns(); x++) {
                Cell cell = grid.getCell(y, x);
                int state = cell.getState(); // get state of each cell
                switch (state) {
                    case 1 -> cell.setState(2);
                    case 2 -> cell.setState(3);
                    case 3 -> {
                        if (electronHist[y][x] == 1 || electronHist[y][x] == 2) {
                            cell.setState(1);
                        }
                    }
                }
            }
        }
    }

    /**
     * Method read file to {@code ArrayList<String>} object and return them.
     *
     * @param path given file path
     */
    public ArrayList<String> readWWP(String path) {
        if (!path.endsWith(".wwp")) {
            throw new IllegalArgumentException();
        }
        // add file to scanner
        Scanner scan = loadFile(path);

        // create arrayList
        return readFile(scan);
    }

    /**
     * Method set appropriate state for each cell of the grid according to given {@code ArrayList} param. <p>
     * 0 - Background
     * 1 - Electron head
     * 2 - Electron tail
     * 3 - Conductor
     *
     * @param list given list
     */
    private void setStates(ArrayList<String> list) {
        for (int y = 0; y < list.size(); y++) {
            String line = list.get(y);
            for (int x = 0; x < line.length(); x++) {
                int state = Integer.parseInt(String.valueOf(line.charAt(x))); // needed to convert char to string first and then string to int
                grid.getCell(y, x).setState(state);
            }
        }
    }

    /**
     * Helper method read file and return arrayList of strings.
     *
     * @param scan given Scanner object
     * @return list of strings
     */
    private ArrayList<String> readFile(Scanner scan) {
        ArrayList<String> list = new ArrayList<>();
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if (!line.startsWith("#")) {
                list.add(line);
            }
        }
        return list;
    }

    /**
     * Helper method which load file under given path to Scanner object
     *
     * @param path given string with file path
     * @return Scanner object with loaded file if exist
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

    public static class WireWorldPattern {

    }
}
