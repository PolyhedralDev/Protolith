import com.dfsek.protolith.data.optics.prism.Prism;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrismTests {
    private static final Prism<Integer, Integer, Double, Double> WHOLE = new Prism<>(
            x -> properFraction(x)
                    .apply((n, f) -> {
                if(f == 0) return Either.right(x);
                else return Either.left(n);
            }),
            Double::valueOf
    );

    public static Tuple2<Integer, Double> properFraction(double x) {
        int ix = (int) Math.floor(x);
        double frac = x - ix;
        return new Tuple2<>(ix, frac);
    }

    @Test
    public void testPrism() {
        assertEquals(Either.right(1.0), WHOLE.match(1.0));
        assertEquals(Either.left(5), WHOLE.match(5.5));
    }
}
