package r15;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Gra w życie autorstwa Conwaya.
 */
public class Conway extends Automaton {

    /**
     * Tworzy siatkę ze światłami ulicznymi (Blinker) oraz szybowcem (Glider).
     */
    public Conway() {
        grid = new GridCanvas(30, 25, SIZE);
        grid.turnOn(1, 2);
        grid.turnOn(2, 2);
        grid.turnOn(3, 2);
        grid.turnOn(6, 1);
        grid.turnOn(7, 2);
        grid.turnOn(7, 3);
        grid.turnOn(8, 1);
        grid.turnOn(8, 2);
    }

    /**
     * Tworzy siatkę na podstawie danych zapisanych w zwykłym pliku tekstowym.
     * http://www.conwaylife.com/wiki/Plaintext
     * 
     * @param path ścieżka dostępu do pliku
     * @param margin liczba komórek do dodania
     */
    public Conway(String path, int margin) {

        // otwórz plik znajdujący się w podanym miejscu
        Scanner scan = null;
        try {
            File file = new File(path);
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // wczytaj zawartość pliku do pamięci operacyjnej
        ArrayList<String> data = new ArrayList<String>();
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            // dodaj tylko wiersze niebędące komentarzami
            if (!line.startsWith("!")) {
                data.add(line);
            }
        }

        // określ liczbę wierszy i kolumn dla wzorca
        int rows = data.size();
        int cols = 0;
        for (String line : data) {
            if (cols < line.length()) {
                cols = line.length();
            }
        }
        if (rows == 0 || cols == 0) {
            throw new IllegalArgumentException("nie odnaleziono komórek");
        }

        // utwórz wynikową siatkę z marginesem dodatkowych komórek
        grid = new GridCanvas(rows + 2 * margin, cols + 2 * margin, SIZE);
        for (int r = 0; r < rows; r++) {
            String line = data.get(r);
            for (int c = 0; c < line.length(); c++) {
                char x = line.charAt(c);
                if (x == 'O') {
                    grid.turnOn(r + margin, c + margin);
                }
            }
        }
    }

    /**
     * Zlicza żywych sąsiadów komórki.
     * 
     * @param r indeks wiersza
     * @param c indeks kolumny
     * @return liczba żywych sąsiadów
     */
    private int countAlive(int r, int c) {
        int count = 0;
        count += grid.test(r - 1, c - 1);
        count += grid.test(r - 1, c);
        count += grid.test(r - 1, c + 1);
        count += grid.test(r, c - 1);
        count += grid.test(r, c + 1);
        count += grid.test(r + 1, c - 1);
        count += grid.test(r + 1, c);
        count += grid.test(r + 1, c + 1);
        return count;
    }

    /**
     * Stosuje reguły aktualizacji siatki zgodnie z zasadami gry w życie.
     * 
     * @param cell komórka, która ma zostać zaktualizowana
     * @param count liczba żywych sąsiadów
     */
    private static void updateCell(Cell cell, int count) {
        if (cell.isOn()) {
            if (count < 2 || count > 3) {
                // Każda żywa komórka z mniej niż dwoma żywymi sąsiadami umiera,
                // niejako z powodu samotności.
                // Każda żywa komórka z więcej niż trzema żywymi sąsiadami umiera,
                // niejako z powodu zatłoczenia.
                cell.turnOff();
            }
        } else {
            if (count == 3) {
                // Każda martwa  komórka z dokładnie trzema żywymi sąsiadami
                // staje się żywa, niejako w wyniku reprodukcji.
                cell.turnOn();
            }
        }
    }

    /**
     * Zlicza sąsiadów przed jakąkolwiek zmianą.
     * 
     * @return liczby sąsiadów każdej komórki
     */
    private int[][] countNeighbors() {
        int rows = grid.numRows();
        int cols = grid.numCols();

        int[][] counts = new int[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                counts[r][c] = countAlive(r, c);
            }
        }
        return counts;
    }

    /**
     * Aktualizuje każdą komórkę w oparciu o liczniki sąsiadów.
     * 
     * @param counts liczby sąsiadów każdej komórki
     */
    private void updateGrid(int[][] counts) {
        int rows = grid.numRows();
        int cols = grid.numCols();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = grid.getCell(r, c);
                updateCell(cell, counts[r][c]);
            }
        }
    }

    /**
     * Symuluje jedna rundę grę w życie.
     */
    public void update() {
        int[][] counts = countNeighbors();
        updateGrid(counts);
    }

    /**
     * Tworzy i uruchamia symulację.
     * 
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        String title = "Gra w życie autorstwa Conwaya";
        Conway game = new Conway("E:\\Developer Start\\Java Projekty\\GameOfLife\\src\\r15\\pulsar.cells", 3);
        game.run(title, 2);
    }

}
