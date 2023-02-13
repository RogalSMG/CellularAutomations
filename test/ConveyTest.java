import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConveyTest {
    Convey convey = new Convey();

    @Test
    void isNumber() {
        assertAll(
                () -> assertEquals(120,convey.isNumber(new StringBuilder("120b"), 2))
        );
    }
}