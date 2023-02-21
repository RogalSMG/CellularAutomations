import java.util.Random;
import java.util.function.Supplier;


public class Ant {
    private int xpos;
    private int ypos;
    private int head;

    public Ant(int rows, int cols, int head) {
        this.xpos = cols / 2;
        this.ypos = rows / 2;
        this.head = head;
    }

    public Ant(int rows, int cols) {
        this(rows, cols, 0);
    }

    public Ant(int bound) {
        this.xpos = randomNumber(bound);
        this.ypos = randomNumber(bound);
        this.head = 0;
    }

    public int randomNumber(int bound) {
        Random r = new Random();
        return r.nextInt(0, bound);
    }

    public int getXpos() {
        return xpos;
    }

    public int getYpos() {
        return ypos;
    }

    public int getHead() {
        return head;
    }

    public void setXpos(int xpos) {
        this.xpos = xpos;
    }

    public void setYpos(int ypos) {
        this.ypos = ypos;
    }

    public void setHead(int head) {
        this.head = head;
    }
}
