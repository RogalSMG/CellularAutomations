import javax.swing.*;

/**
 * Class contain method allowing to run different simulation based on {@code GridCanvas} class
 */
public abstract class Automat {
    protected GridCanvas grid;

    /**
     * Method create new frame and call {@code mainLoop}. <p>
     * Rate is equal 1000 / rate.
     * @param title string value of frame title
     * @param rate int value to set frequency of update
     */
    public void run(String title, int rate) {
        JFrame jFrame = new JFrame(title);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.add(this.grid);
        jFrame.pack();
        jFrame.setVisible(true);
        this.mainLoop(rate);
    }

    /**
     * Main loop where simulation is started
     * @param rate int value to set frequency of update
     */
    private void mainLoop(int rate) {
       grid.repaint();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            this.update();
            grid.repaint();
            try {
                Thread.sleep(1000 / rate);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public abstract void update();
}
