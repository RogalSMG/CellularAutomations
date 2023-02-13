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
        grid = new GridCanvas(rows, cols, 10);
        this.xpos = rows / 2;
        this.ypos = cols / 2;
        head = 0;
    }

    public static void main(String[] args) {
        Langton langton = new Langton(10, 10);
        langton.run("Ant", 2);
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
        Cell cell = grid.getCell(ypos, xpos);
        if (cell.isOff()) {
            cell.turnOn();
            head = (head + 1) % 4; // change
        } else if (cell.isOn()) {
            cell.turnOff();
            head = (head + 3) % 4;
        }
    }

    /**
     * Change position of ant
     * Directions: 0 - North, 1 - East, 2 - South, 3 - West
     */
    private void moveAnt() {
        if (head == 0) {

            ypos--;
        } else if (head == 1) {
            xpos++;
        } else if (head == 2) {
            ypos++;
        } else {
            xpos--;
        }
    }
}
