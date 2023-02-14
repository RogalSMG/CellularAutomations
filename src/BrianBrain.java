import java.awt.*;

public class BrianBrain extends Automat {


    public BrianBrain() {
        Color[] colors = {Color.WHITE, Color.BLACK, Color.GRAY};
        Cell.setCOLORS(colors);
        grid = new GridCanvas(20,20,20);
        grid.setGoThroughBorder(true);
        grid.randomSetState(0,3);
    }

    public static void main(String[] args) {
        BrianBrain brianBrain = new BrianBrain();
        brianBrain.run("Brian Game", 5);

    }

    /**
     * Method simulate one round of Brian's Brain automation cell game
     */
    @Override
    public void update() {
        int[][] hist = grid.countNeighbors(1);
        int alive = grid.countState(1);
        int dying = grid.countState(2);
        System.out.println("Alive: " + alive + " Dying: " + dying);
        cellUpdate(hist);

    }

    private void cellUpdate(int[][] hist) {
        for (int y = 0; y < grid.numRows(); y++) {
            for (int x = 0; x < grid.numColumns(); x++) {
                Cell cell = grid.getCell(y, x);
                int state = cell.getState();
                switch (state) {
                    case 0 -> {
                        if (hist[y][x] == 2) { // if dead cell have exact 2 alive neighbors
                            cell.setState(1); // get alive, set status to 1
                        }
                    }
                    case 1 -> cell.setState(2); // alive (1) cell change status to dying (2)
                    case 2 -> cell.setState(0); // dying (2) cell change status to dead (0)
                }
            }
        }
    }
}
