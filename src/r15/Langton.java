package r15;

/**
 * Mrówka Langtona.
 */
public class Langton extends Automaton {

    private int xpos;
    private int ypos;
    private int head;   // 0 = północ, 1 = wschód, 2 = południe, 3 = zachód

    /**
     * Tworzy siatkę z mrówką w środku.
     * 
     * @param rows liczba wierszy
     * @param cols liczba kolumn
     */
    public Langton(int rows, int cols) {
        grid = new GridCanvas(rows, cols, 10);
        xpos = rows / 2;
        ypos = cols / 2;
        head = 0;
    }

    /**
     * Przełącza kolor bieżącej komórki.
     */
    private void flipCell() {
        Cell cell = grid.getCell(xpos, ypos);
        if (cell.isOff()) {
            // na białym polu; skręć w prawo i przełącz kolor
            head = (head + 1) % 4;
            cell.turnOn();
        } else {
            // na czarnym polu; skręć w lewo i przełącz kolor
            head = (head + 3) % 4;
            cell.turnOff();
        }
    }

    /**
     * Przesun mrówkę do przodu o jedną jednostkę.
     */
    private void moveAnt() {
        if (head == 0) {
            ypos -= 1;
        } else if (head == 1) {
            xpos += 1;
        } else if (head == 2) {
            ypos += 1;
        } else {
            xpos -= 1;
        }
    }

    /**
     * Symuluje jedną rundę mrówki Langtona.
     */
    public void update() {
        flipCell();
        moveAnt();
    }

    /**
     * Tworzy i uruchamia symulację.
     * 
     * @param args argumenty wiersza poleceń
     */
    public static void main(String[] args) {
        String title = "Mrówka Langtona";
        Langton game = new Langton(61, 61);
        game.run(title, 750);
    }

}
