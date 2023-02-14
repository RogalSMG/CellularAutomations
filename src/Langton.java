public class Langton extends Automat {
    private int xpos;
    private int ypos;
    private int head;

    /**
     * Constructs a new langton object. Set number of columns and rows of grid.
     *
     * @param rows number of rows
     * @param cols number of columns
     */
    public Langton(int rows, int cols) {
        grid = new GridCanvas(rows, cols, 20);
        this.xpos = rows / 2;
        this.ypos = cols / 2;
        head = 0;
    }

    public static void main(String[] args) {
        Langton langton = new Langton(8, 8);
        langton.run("Ant", 10);
    }

    /**
     * Method represent one round of game, flip cell and move. Game rules: <p>
     * - If cell where ant is currently standing is white then change color to black, turn right and move one cell forward.
     * - If cell where ant is currently standing is black then change color to white, turn left and move one cell forward.
     */
    public void update() {
        flipCell();
        moveAnt();
    }

    /**
     * Change state of cell. If was white, change to black and vice versa. Also set new {@code head} for ant
     */
    private void flipCell() {
        Cell cell = null;
        try {
            cell = grid.getCell(ypos, xpos);
        } catch (IndexOutOfBoundsException e) { // exception handler change position of head if is out of the grid
            if (ypos == -1) {
                ypos = grid.numRows() - 1;
            } else if (ypos == grid.numRows()) {
                ypos = 0;
            } else if (xpos == -1) {
                xpos = grid.numColumns() - 1;
            } else if (xpos == grid.numColumns()) {
                xpos = 0;
            }
            cell = grid.getCell(ypos, xpos);
        } finally {
            assert cell != null;
            if (cell.isOff()) {
                cell.turnOn();
                head = (head + 1) % 4; // turn right
            } else if (cell.isOn()) {
                cell.turnOff();
                head = (head + 3) % 4; // turn left
            }
        }
    }

    /**
     * Change position of ant
     * Directions: 0 - North, 1 - East, 2 - South, 3 - West
     */
    private void moveAnt() {
        switch (head) {
            case 0 -> ypos++;
            case 1 -> xpos++;
            case 2 -> ypos--;
            case 3 -> xpos--;
        }
    }
}
