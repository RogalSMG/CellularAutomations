import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConwayTest {
    Conway conway = new Conway();

    @Test
    void isNumber() {
        assertAll(
                () -> assertEquals(120, conway.isNumber(new StringBuilder("120b"), 2))
        );
    }
}