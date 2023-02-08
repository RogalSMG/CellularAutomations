package r15;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Kwadrat znajdujący się w ustalonym miejscu, który zmienia kolor.
 */
public class Cell {

    public static final Color[] COLORS = {Color.WHITE, Color.BLACK};

    private final int x;
    private final int y;
    private final int size;
    private int state;

    /**
     * Konstruuje nową komórkę, która początkowo jest wyłączona.
     * 
     * @param x współrzędna X
     * @param y współrzędna Y
     * @param size liczba pikseli
     */
    public Cell(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.state = 0;
    }

    /**
     * Rysuje komórkę na ekranie.
     * 
     * @param g kontekst graficzny
     */
    public void draw(Graphics g) {
        g.setColor(COLORS[state]);
        g.fillRect(x + 1, y + 1, size - 1, size - 1);
        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(x, y, size, size);
    }

    /**
     * @return true, jeśli komórka jest wyłączona
     */
    public boolean isOff() {
        return state == 0;
    }

    /**
     * @return true, jeśli komórka jest włączona
     */
    public boolean isOn() {
        return state == 1;
    }

    /**
     * Ustawia stan komórki na wyłączony.
     */
    public void turnOff() {
        state = 0;
    }

    /**
     * Ustawia stan komórki na włączony.
     */
    public void turnOn() {
        state = 1;
    }
}
