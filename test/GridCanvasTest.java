import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;;

class GridCanvasTest {

    GridCanvas gridCanvas = new GridCanvas(10, 10, 20);
    Conway conway = new Conway("E:\\Developer Start\\Java Projekty\\GameOfLife\\test\\testTestIfOn.cell", 0, 20);

    @BeforeAll
    static void beforeAll() {

    }

    @BeforeEach
    void before() {
        gridCanvas.turnOn(0, 1);
        gridCanvas.turnOn(1, 2);
        gridCanvas.turnOn(0, 0);
        gridCanvas.turnOn(1, 0);
        gridCanvas.turnOn(3, 0);
    }

    @AfterEach
    void afterEach() {

    }

    @Test
    void countAlive() {
        assertAll("Testing",
                () -> assertEquals(5, gridCanvas.countState(1))
        );
    }

    @Test
    void testTestIfOn() {
        assertAll(
                () -> assertEquals(1, conway.getGrid().checkState(0, 0, 1)),
                () -> assertEquals(1, conway.getGrid().checkState(0, 2, 1)),
                () -> assertEquals(1, conway.getGrid().checkState(1, 5, 1)),
                () -> assertEquals(1, conway.getGrid().checkState(0, 0, 1)),
                () -> assertEquals(1, conway.getGrid().checkState(4, 5, 1)),
                () -> assertEquals(0, conway.getGrid().checkState(0, 1, 1))
        );
    }

    @Test
    void testCountNeight() {
        conway.grid.setGoThroughBorder(true);
        assertAll(
                () -> assertEquals(3, conway.getGrid().countNeighbors(0,0, 1)),
                () -> assertEquals(4, conway.getGrid().countNeighbors(0,1, 1)),
                () -> assertEquals(1, conway.getGrid().countNeighbors(0,2, 1)),
                () -> assertEquals(2, conway.getGrid().countNeighbors(0,3, 1)),
                () -> assertEquals(2, conway.getGrid().countNeighbors(0,4, 1)),
                () -> assertEquals(2, conway.getGrid().countNeighbors(4,5, 1)),
                () -> assertEquals(3, conway.getGrid().countNeighbors(1,0, 1)),
                () -> assertEquals(3, conway.getGrid().countNeighbors(1,1, 1))
        );
    }
}
