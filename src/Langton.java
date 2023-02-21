import java.util.ArrayList;

public class Langton extends Automat {
    private ArrayList<Ant> ants;


    /**
     * Constructs a new langton object. Set number of columns and rows of grid.
     *
     * @param rows number of rows
     * @param cols number of columns
     */
    public Langton(int rows, int cols) {
        grid = new GridCanvas(rows, cols, 20);
        ants = new ArrayList<>();
    }

    /**
     * Wrapper method for adding
     * @param ant
     */
    public void addAnt(Ant ant) {
        this.ants.add(ant);
    }
    /**
     * Create and run simulation
     *
     * @param args command prompt argument
     */
    public static void main(String[] args) {
        Langton langton = new Langton(10, 10);
        Ant antA = new Ant(5, 5);
        Ant antB = new Ant(8, 8);
        Ant antC = new Ant(8, 8);
        langton.addAnt(antC);
        langton.addAnt(antB);
        langton.run("Ant", 4);
    }

    /**
     * Method represent one round of game, flip cell and move. Game rules: <p>
     * - If cell where ant is currently standing is white then change color to black, turn right and move one cell forward.
     * - If cell where ant is currently standing is black then change color to white, turn left and move one cell forward.
     */
    public void update() {
        for (Ant ant : ants) {
            flipCell(ant);
            moveAnt(ant);
        }
    }

    /**
     * Change state of cell. If was white, change to black and vice versa. Also set new {@code head} for ant
     */
    private void flipCell(Ant ant) {
        Cell cell = null;
        try {
            cell = grid.getCell(ant.getYpos(), ant.getXpos());
        } catch (IndexOutOfBoundsException e) { // exception handler, change position of head if is out of the grid
            if (ant.getYpos() == -1) {
                ant.setYpos(grid.numRows() - 1);
            } else if (ant.getYpos() == grid.numRows()) {
                ant.setYpos(0);
            } else if (ant.getXpos() == -1) {
                ant.setXpos(grid.numColumns() - 1);
            } else if (ant.getXpos() == grid.numColumns()) {
                ant.setXpos(0);
            }
            cell = grid.getCell(ant.getYpos(), ant.getXpos());
        } finally {
            assert cell != null;
            if (cell.isOff()) {
                cell.turnOn();
                ant.setHead((ant.getHead() + 1) % 4); // turn right
            } else if (cell.isOn()) {
                cell.turnOff();
                ant.setHead((ant.getHead() + 3) % 4); // turn left
            }
        }
    }

    /**
     * Change position of ant
     * Directions: 0 - North, 1 - East, 2 - South, 3 - West
     */
    private void moveAnt(Ant ant) {
        switch (ant.getHead()) {
            case 0 -> ant.setYpos(ant.getYpos() + 1);
            case 1 -> ant.setXpos(ant.getXpos() + 1);
            case 2 -> ant.setYpos(ant.getYpos() - 1);
            case 3 -> ant.setXpos(ant.getXpos() - 1);
        }
    }
}
