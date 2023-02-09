import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Convey {
    private final GridCanvas grid;

    public Convey() {
        grid = new GridCanvas(30, 25, 20);
        grid.turnOn(1, 2);
        grid.turnOn(2, 2);
        grid.turnOn(3, 2);
        grid.turnOn(6, 1);
        grid.turnOn(7, 2);
        grid.turnOn(7, 3);
        grid.turnOn(8, 1);
        grid.turnOn(8, 2);

    }

    /**
     * Convey constructor allow to create :
     * O - Alive cells
     * . - dead cells
     *
     * @param path   of file
     * @param margin amount of cell around given pattern
     */
    public Convey(String path, int margin) {

        if (path.endsWith("rle")) {
            rleConvey(path);
        }

        // load file to scanner
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

        // new GridCanvas
        grid = new GridCanvas(height + margin * 2, width + margin * 2, 20);

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

    private void rleConvey(String path) {

    }

    public static void main(String[] args) {
        String title = "Game Of Life";
        Convey game = new Convey("E:\\Developer Start\\Java Projekty\\GameOfLife\\src\\patterns\\glider.cells", 1);
        JFrame jFrame = new JFrame(title);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // setting close operation, field EXIT_ON_CLOSE is static
        jFrame.setResizable(false); // set if frame can be resizeable
        jFrame.add(game.grid); // add canvas to frame
        jFrame.pack(); // set frame size to be able to hold canvas
        jFrame.setVisible(true); // set if window have to be visible
        game.mainLoop();
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
     * Standard method which contain steps of game
     */
    private void mainLoop() {
        while (true) {
            this.update();
            grid.repaint();
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                // handling not necessary
            }

        }
    }

    /**
     * Method simulate one round of Game of life
     */
    private void update() {
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
