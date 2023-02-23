import java.util.ArrayList;
import java.util.Random;

public class Langton extends Automat {
    private final ArrayList<Ant> ants;


    /**
     * Constructs a new Langton object. Set number of columns and rows of grid.
     *
     * @param rows number of rows
     * @param cols number of columns
     */
    public Langton(int rows, int cols) {
        grid = new GridCanvas(rows, cols, 20);
        ants = new ArrayList<>();
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
     * Wrapper method for adding
     *
     * @param ant Ant obj to add
     */
    public void addAnt(Ant ant) {
        this.ants.add(ant);
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
     *
     * @param ant next ant
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
     *
     * @param ant next ant
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

/**
 * Inner class represent an {@code Ant} object
 */
class Ant {
    private int xpos;
    private int ypos;
    private int head;

    /**
     * Standard constructor
     *
     * @param x    coordinate of x-axis
     * @param y    coordinate of y-axis
     * @param head direction of ant
     */
    public Ant(int x, int y, int head) {
        this.xpos = y;
        this.ypos = x;
        this.head = head;
    }

    /**
     * Constructor where only coordinators are required
     *
     * @param x coordinate of x-axis
     * @param y coordinate of y-axis
     */
    public Ant(int x, int y) {
        this(x, y, 0);
    }

    /**
     * Constructor where coordination are random from 0 to upper bound determined by parameter {@code bound}
     *
     * @param bound upper bound, exclusive
     */
    public Ant(int bound) {
        this.xpos = randomNumber(bound);
        this.ypos = randomNumber(bound);
        this.head = 0;
    }

    /**
     * Helper method
     *
     * @param bound upper bound, exclusive
     * @return number of range from 0 to bound
     */
    public int randomNumber(int bound) {
        Random r = new Random();
        return r.nextInt(0, bound);
    }

    /**
     * Getter method for {@code xpos} field
     *
     * @return this.xpos
     */
    public int getXpos() {
        return xpos;
    }

    /**
     * Setter method for {@code xpos} field
     *
     * @param xpos given number to set
     */
    public void setXpos(int xpos) {
        this.xpos = xpos;
    }

    /**
     * Getter method for {@code ypos} field
     *
     * @return this.ypos
     */
    public int getYpos() {
        return ypos;
    }

    /**
     * Setter method for {@code ypos} field
     *
     * @param ypos given number to set
     */
    public void setYpos(int ypos) {
        this.ypos = ypos;
    }

    /**
     * Getter method for {@code head} field
     *
     * @return this.head
     */
    public int getHead() {
        return head;
    }

    /**
     * Setter method for {@code head} field
     *
     * @param head given number to set
     */
    public void setHead(int head) {
        this.head = head;
    }
}


