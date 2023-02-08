package r15;

import java.awt.Canvas;
import java.awt.Graphics;

/**
 * Dwuwymiarowa tablica komórek reprezentującą prostokątną siatkę.
 */
public class GridCanvas extends Canvas {

    /** Komórki przechowywane w porządku z ważniejszymi wierszami. */
    private Cell[][] array;

    /**
     * Konstruuje siatkę o podanej wielkości.
     * 
     * @param rows liczba wierszy
     * @param cols liczba kolumn
     * @param size liczba pikseli na komórkę
     */
    public GridCanvas(int rows, int cols, int size) {

        // zbuduj dwuwymiarową tablicę komórek
        array = new Cell[rows][cols];
        for (int r = 0; r < rows; r++) {
            int y = r * size;
            for (int c = 0; c < cols; c++) {
                int x = c * size;
                array[r][c] = new Cell(x, y, size);
            }
        }

        // ustaw wielkość kanwy
        setSize(cols * size, rows * size);
    }

    /**
     * @return liczba wierszy
     */
    public int numRows() {
        return array.length;
    }

    /**
     * @return liczba kolumn
     */
    public int numCols() {
        return array[0].length;
    }

    /**
     * @param r indeks wiersza
     * @param c indeks kolumny
     * @return komórka
     */
    public Cell getCell(int r, int c) {
        return array[r][c];
    }

    /**
     * Metoda pomocnicza umożliwiająca włączenie komórki znajdującej się w miejscu określanym przez (r, c).
     * 
     * @param r indeks wiersza
     * @param c indeks kolumny
     */
    public void turnOn(int r, int c) {
        array[r][c].turnOn();
    }

    /**
     * Zwraca 1, jeśli komórka znajdująca się w miejscu (r, c) istnieje i jest włączona.
     * Zwraca 0, jeśli komórka ta nie istnieje lub jest wyłączona.
     * 
     * @param r indeks wiersza
     * @param c indeks kolumny
     * @return 1 lub 0
     */
    public int test(int r, int c) {
        try {
            if (array[r][c].isOn()) {
                return 1;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            // komórka nie istnieje
        }
        return 0;
    }

    /**
     * Rysuje siatkę, komórka po komórce.
     * 
     * @param g kontekst graficzny
     */
    public void draw(Graphics g) {
        for (Cell[] row : array) {
            for (Cell cell : row) {
                cell.draw(g);
            }
        }
    }

    /**
     * Wyświetla siatkę na ekranie.
     * 
     * @param g kontekst graficzny
     */
    public void paint(Graphics g) {
        draw(g);
    }

    /**
     * Przesłonięcie tej metody sprawia, że symulacja działa płynniej.
     * Standardowo obiekt klasy Canvas jest czyszczony przed wyświetlaniem,
     * czyszczenie to nie jest jednak potrzebne, ponieważ metoda paint rysuje całą siatkę.
     * 
     * @param g kontekst graficzny
     */
    public void update(Graphics g) {
        draw(g);
    }


}
