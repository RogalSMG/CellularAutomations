package r15;

import javax.swing.JFrame;

/**
 * Automat komórkowy składa się z siatki komórek i działa zgodnie z zestawem
 * reguł, według których siatka ta jest aktualizowana wraz z upływem czasu.
 */
public abstract class Automaton {

    public static final int SIZE = 20;

    protected GridCanvas grid;

    /**
     * Stosuje reguły w celu aktualizacji siatki.
     */
    public abstract void update();

    /**
     * Tworzy obiekt klasy JFrame i uruchamia automat.
     * 
     * @param title tytuł ramki
     * @param rate liczba kroków na sekundę
     */
    public void run(String title, int rate) {
        // ustaw ramkę okna
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(this.grid);
        frame.pack();
        frame.setVisible(true);
        this.mainloop(rate);
    }

    /**
     * Pętla symulacji.
     * 
     * @param rate liczba kroków na sekundę
     */
    private void mainloop(int rate) {
        while (true) {

            // zaktualizuj rysunek
            this.update();
            grid.repaint();

            // opóźnij symulację
            try {
                Thread.sleep(1000 / rate);
            } catch (InterruptedException e) {
                // nie rób nic
            }
        }
    }

}
