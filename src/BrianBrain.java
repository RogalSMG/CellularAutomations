import java.awt.*;

public class BrianBrain extends Automat {
public static Color[] COLORS = {Color.WHITE, Color.BLACK, Color.GRAY}; // new set of colors for Brian's Brain game
    /**
     * Standard constructor where dimensions are hardcoded
     */
    public BrianBrain() {
        Cell.setCOLORS(COLORS);
        grid = new GridCanvas(20, 20, 20);
        grid.setGoThroughBorder(true);
        grid.randomSetState(0, 3);
    }

    /**
     * Constructor where size of grid is specified by parameters
     * @param rows number of rows for grid
     * @param cols number of columns for grid
     */
    public BrianBrain(int rows, int cols) {
        Cell.setCOLORS(COLORS);
        grid = new GridCanvas(rows, cols, 20);
        grid.setGoThroughBorder(true);
        grid.randomSetState(0, 3);
    }

    /**
     * Create and run simulation
     * @param args command prompt argument
     */
    public static void main(String[] args) {
        BrianBrain brianBrain = new BrianBrain(53,98);
        brianBrain.run("Brian Game", 5);

    }

    /**
     * Method simulate one round of Brian's Brain automation cell game
     */
    @Override
    public void update() {
        int[][] hist = grid.countNeighbors(1);
        int alive = grid.countState(1);
        int dying = grid.countState(2);
        System.out.println("Alive: " + alive + " Dying: " + dying);
        cellUpdate(hist, 2);

    }

    /**
     * Method implement rules of Brian's Brain game:
     * - Dead cells alive if they were surround by {@code aliveCells} amount of alive cell
     * - Alive cells change to dying
     * - Dying cells change to dead
     *
     * @param hist two dimension int array included amount of cells which are alive in the neighborhood for each {@code cell} object in the {@code this.grid}
     * @param aliveCells alive cells around specified cell needed to revive this cell
     */
    private void cellUpdate(int[][] hist, int aliveCells) {
        for (int y = 0; y < grid.numRows(); y++) {
            for (int x = 0; x < grid.numColumns(); x++) {
                Cell cell = grid.getCell(y, x);
                int state = cell.getState();
                switch (state) {
                    case 0 -> {
                        if (hist[y][x] == aliveCells) { // if dead cell have exact 2 alive neighbors
                            cell.setState(1); // get alive, set status to 1
                        }
                    }
                    case 1 -> cell.setState(2); // alive (1) cell change status to dying (2)
                    case 2 -> cell.setState(0); // dying (2) cell change status to dead (0)
                }
            }
        }
    }
}
