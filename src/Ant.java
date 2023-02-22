import java.util.Random;

/**
 * Class represent Ant object
 */
public class Ant {
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
