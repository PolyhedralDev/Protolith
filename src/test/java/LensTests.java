import com.dfsek.protolith.data.optics.lens.Lens;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LensTests {
    public static Lens<Boolean, Boolean, Integer, Integer> SIGN = new Lens<>(
            x -> x >= 0,
            (b, x) -> {
                if(b) return Math.abs(x);
                else return -Math.abs(x);
            }
    );

    @Test
    public void testSignLens() {
        assertTrue(SIGN.view(2));
        assertFalse(SIGN.view(-2));

        assertEquals(2, SIGN.update(true, -2));
        assertEquals(2, SIGN.update(true, 2));
        assertEquals(-2, SIGN.update(false, -2));
        assertEquals(-2, SIGN.update(false, -2));
    }
}
