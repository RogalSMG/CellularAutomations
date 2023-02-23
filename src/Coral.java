import java.awt.*;
import java.util.Random;

public class Coral extends Automat {

    public Coral() {
        Color[] COLORS = {Color.BLACK, Color.CYAN};
        Cell.setCOLORS(COLORS);
        grid = new GridCanvas(200, 200, 5);
        turnOnSomeCell(95,95,4,4,60);
    }

    public static void main(String[] args) {
        Coral coral = new Coral();
        coral.run("Coral", 5);

    }

    @Override
    public void update() {
        int[][] states = grid.countNeighbors(1);
        updateGrid(states);
    }

    /**
     * Turning on random cells on {@code grid}.
     * @param r number of row where start
     * @param c number of column where start
     * @param x size to x direction
     * @param y size to y direction
     * @param percent percent of turning on cells from 0 to 100
     */
    public void turnOnSomeCell(int r, int c, int x, int y, int percent) {
        Random ran = new Random();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                int numb = ran.nextInt(1,101);
                if (numb <= percent) {
                    grid.getCell(i + r, j + c).turnOn();
                }
            }
        }
    }

    /**
     * Method go through whole grid and call {@code updateCell} method for each cell.
     *
     * @param states given histogram of neighbors.
     */
    private void updateGrid(int[][] states) {
        int cols = grid.numColumns();
        int rows = grid.numRows();

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                Cell cell = grid.getCell(y, x);
                updateCell(cell, states[y][x]);
            }
        }
    }

    /**
     * Method implement rules of Coral Cellular Automaton
     * @param cell current separate cell
     * @param state number of neighbors of current cell
     */
    private void updateCell(Cell cell, int state) {
        if (state == 3) {
            cell.turnOn();
        } else if (state <= 2) {
            cell.turnOff();
        }
    }
}
