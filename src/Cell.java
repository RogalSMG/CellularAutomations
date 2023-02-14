import java.awt.*;

/**
 * Class represent single cell
 */
public class Cell {
    private static Color[] COLORS = {Color.WHITE, Color.BLACK};
    private final int x;
    private final int y;
    private final int size;
    private int state;

    /**
     * Constructor initialize new Cell obj with specified x and y coordination's and size. Default is turn off
     *
     * @param x    coordination x
     * @param y    coordination y
     * @param size pixel number
     */
    public Cell(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.state = 0;
    }

    /**
     * Setter for colors
     *
     * @param COLORS color to set
     */
    public static void setCOLORS(Color[] COLORS) {
        Cell.COLORS = COLORS;
    }

    /**
     * Method check if this cell is alive (state 1)
     *
     * @return true if is alive, else false
     */
    public boolean isOn() {
        return state == 1;
    }

    /**
     * Method check if this cell is dead (state 0)
     *
     * @return true if is dead, else true
     */
    public boolean isOff() {
        return state == 0;
    }

    /**
     * Set state to 0, as dead
     */
    public void turnOff() {
        state = 0;
    }

    /**
     * Set state of cell to 1, as alive
     */
    public void turnOn() {
        state = 1;
    }

    /**
     * Set cell state to 2
     */
    public void turnDying() {
        state = 2;
    }

    /**
     * Method check if cell is in dying phase (state 2)
     *
     * @return true if state is 2, otherwise false
     */
    public boolean isDying() {
        return state == 2;
    }

    /**
     * Method draw cell
     *
     * @param g Graphic class
     */
    public void draw(Graphics g) {
        g.setColor(COLORS[state]); // set first color for cell
        g.fillRect(x + 1, y + 1, size - 1, size + 1); // draw filled rect with suit state - black or white
        g.setColor(Color.LIGHT_GRAY); // set color to draw edges
        g.drawRect(x, y, size, size); // draw edges of cell
    }

    /**
     * Getter for state field
     *
     * @return this.state
     */
    public int getState() {
        return this.state;
    }

    /**
     * Setter for state field
     *
     * @param state given state to set
     */
    public void setState(int state) {
        this.state = state;
    }
}
