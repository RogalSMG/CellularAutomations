import java.awt.*;

public class GridCanvas extends Canvas {
    private Cell[][] grid;

    /**
     * fill two dimension Array with Cell objects
     *
     * @param rows number of rows
     * @param cols number of columns
     * @param size size of each cell
     */
    public GridCanvas(int rows, int cols, int size) {
        this.grid = new Cell[rows][cols];
        for (int r = 0; r < rows; r++) {
            int y = r * size;
            for (int c = 0; c < cols; c++) {
                int x = c * size;
                grid[r][c] = new Cell(x, y, size);
            }
        }
        setSize(cols * size, rows * size); // set size of canvas -> płótno
    }

    /**
     * Draw all cells on canvas
     *
     * @param g the specified Graphics context
     */
    public void draw(Graphics g) {
        for (int i = 0; i < grid[0].length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j].draw(g);
            }
        }
    }

    /**
     * Practise method where forEach loops were used by me to make same things like in draw method
     *
     * @param g the specified Graphics context
     */
    public void drawForEach(Graphics g) {
        for (Cell[] row : grid) { // take array of each row of two dimension table
            for (Cell cell : row) { // take Cell of each index of array
                cell.draw(g); // draw each cell
            }
        }
    }

    /**
     * @param g the specified Graphics context
     */
    public void paint(Graphics g) {
        drawForEach(g);
    }

    /**
     * method check if cell under specified parameters is on
     *
     * @param r index of row
     * @param c index of column
     * @return 1 when alive, else 0
     */
    public int testIfOn(int r, int c) {
        try {
            if (grid[r][c].isOn()) {
                return 1;
            }
        } catch (IndexOutOfBoundsException e) {
            int newR = r;
            int newC = c;
            if (r < 0) {
                newR = grid.length - 1;
            } else if (r == grid.length) {
                newR = 0;
            }

            if (c < 0) {
                newC = grid[0].length;
            } else if (c == grid[0].length) {
                newC = 0;
            }
            return testIfOn(newR,newC);

        }
        return 0;
    }

    /**
     * Method count alive neighbourhood of specified cell
     * @param r index of row
     * @param c index of column
     * @return number of alive Cell around specified by r and c
     */
    public int countNeight(int r, int c) {
        int count = 0;
        count += testIfOn(r - 1, c);
        count += testIfOn(r - 1, c - 1);
        count += testIfOn(r, c - 1);
        count += testIfOn(r + 1, c - 1);
        count += testIfOn(r + 1, c);
        count += testIfOn(r + 1, c + 1);
        count += testIfOn(r, c + 1);
        count += testIfOn(r - 1, c + 1);
        return count;
    }

    public int countAlive() {
        int count = 0;
        for (Cell[] rows : grid) {
            for (Cell cell : rows) {
                if (cell.isOn()) {
                    count += 1 ;
                }
            }
        }
        return count;
    }

    /**
     * Method which return number of rows of grid field {@code Array[][]}
     *
     * @return int value of number of rows
     */
    public int numRows() {
        return grid.length;
    }

    /**
     * Method which return cumber of columns of grid field {@code Array[][]}
     *
     * @return int value of number od columns
     */
    public int numColumns() {
        return grid[0].length;
    }

    /**
     * Return cell of specified coordination's
     *
     * @param r row index
     * @param c column index
     * @return Cell object under given coordination's
     */
    public Cell getCell(int r, int c) {
        return grid[r][c];
    }

    /**
     * Turn on cell at specified coordination's
     *
     * @param r row index
     * @param c column index
     */
    public void turnOn(int r, int c) {
        grid[r][c].turnOn();
    }
}
