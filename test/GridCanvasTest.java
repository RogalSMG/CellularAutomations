import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;;

class GridCanvasTest {

    GridCanvas gridCanvas = new GridCanvas(10, 10, 20);
    Convey convey = new Convey("E:\\Developer Start\\Java Projekty\\GameOfLife\\test\\testTestIfOn.cell", 0);

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
                () -> assertEquals(5, gridCanvas.countAlive())
        );
    }

    @Test
    void testTestIfOn() {
        assertAll(
                () -> assertEquals(1, convey.getGrid().testIfOn(0, 0)),
                () -> assertEquals(1, convey.getGrid().testIfOn(0, 2)),
                () -> assertEquals(1, convey.getGrid().testIfOn(1, 5)),
                () -> assertEquals(1, convey.getGrid().testIfOn(0, 0)),
                () -> assertEquals(1, convey.getGrid().testIfOn(4, 5)),
                () -> assertEquals(0, convey.getGrid().testIfOn(0, 1))
        );
    }

    @Test
    void testCountNeight() {
        assertAll(
                () -> assertEquals(3,convey.getGrid().countNeight(0,0)),
                () -> assertEquals(4,convey.getGrid().countNeight(0,1)),
                () -> assertEquals(1,convey.getGrid().countNeight(0,2)),
                () -> assertEquals(2,convey.getGrid().countNeight(0,3)),
                () -> assertEquals(2,convey.getGrid().countNeight(0,4)),
                () -> assertEquals(2,convey.getGrid().countNeight(4,5)),
                () -> assertEquals(4,convey.getGrid().countNeight(1,0)),
                () -> assertEquals(3,convey.getGrid().countNeight(1,1))
        );
    }
}
