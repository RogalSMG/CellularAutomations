import java.awt.*;
import java.util.Random;

public class GridCanvas extends Canvas {
    private final Cell[][] grid;
    private boolean goThroughBorder = false;

    /**
     * Method fill two dimension Array with cell objects
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
     * Setter method for {@code goThroughBorder} field
     *
     * @param goThroughBorder boolean value to set {@code goThroughBorder}
     */
    public void setGoThroughBorder(boolean goThroughBorder) {
        this.goThroughBorder = goThroughBorder;
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
            for (Cell cell : row) { // take cell of each index of array
                cell.draw(g); // draw each cell
            }
        }
    }

    /**
     * Fill whole grid randomly with cells with state 0 or 1
     * @param down down bound of random number inclusive
     * @param upper upper bound of random number exclusive
     */
    public void randomSetState(int down, int upper) {
        Random r = new Random();
        for (Cell[] row : grid) {
            for (Cell cell : row) {
                int state = r.nextInt(down, upper);
                cell.setState(state);
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
     * Method check if cell under specified parameters is on
     *
     * @param r index of row
     * @param c index of column
     * @param state indicate cell state to count
     * @return 1 when state same as given state value, otherwise 0
     */
    public int checkState(int r, int c, int state) {
        try {
            if (grid[r][c].getState() == state) {
                return 1;
            }
        } catch (IndexOutOfBoundsException e) {
            if (goThroughBorder) {
                // exception handler which allow to go through grid borders.
                // if checkState method trying to get cell which is out of the grid size
                // this handler maps coordination to opposite side
                int newR = r;
                int newC = c;
                if (r < 0) {
                    newR = grid.length - 1;
                } else if (r == grid.length) {
                    newR = 0;
                }

                if (c < 0) {
                    newC = grid[0].length - 1;
                } else if (c == grid[0].length) {
                    newC = 0;
                }
                return checkState(newR, newC, state); // return same method where coordination are mapped
            }
        }
        return 0;
    }

    /**
     * Method count neighbourhoods of all cells of {@code this.grid.grid Cell[][] } field.
     * Count cell as a neighborhood only if parameter state is the same as state of separate cell.
     *
     * @param state indicate cell state when can be counted
     * @return number of neighbourhoods of each cell with given state
     */
    public int[][] countNeighbors(int state) {
        int rows = numRows();
        int cols = numColumns();

        int[][] counts = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                counts[i][j] = countNeighbors(i, j, state);
            }
        }
        return counts;
    }

    /**
     * Method count neighbourhoods cells with indicated state for specified cell
     *
     * @param r     index of row
     * @param c     index of column
     * @param state indicate cell state to count
     * @return number of alive Cell around specified by r and c
     */
    public int countNeighbors(int r, int c, int state) {
        int count = 0;
        count += checkState(r - 1, c, state);
        count += checkState(r - 1, c - 1, state);
        count += checkState(r, c - 1, state);
        count += checkState(r + 1, c - 1, state);
        count += checkState(r + 1, c, state);
        count += checkState(r + 1, c + 1, state);
        count += checkState(r, c + 1, state);
        count += checkState(r - 1, c + 1, state);
        return count;
    }

    /**
     * Method count all cells of specified state.
     *
     * @param state indicate cell state to count
     * @return number of alive cells
     */
    public int countState(int state) {
        int count = 0;
        for (Cell[] rows : grid) {
            for (Cell cell : rows) {
                if (cell.getState() == state) {
                    count += 1;
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
