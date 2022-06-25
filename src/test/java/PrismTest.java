import com.dfsek.protolith.optics.interact.Matching;
import com.dfsek.protolith.optics.interact.Re;
import com.dfsek.protolith.optics.interact.View;
import com.dfsek.protolith.optics.prism.SimplePrism;
import io.vavr.Function1;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

import static io.vavr.API.*;
import static org.junit.jupiter.api.Assertions.*;

public class PrismTest {
    private static final SimplePrism<String, Integer> extractInt = SimplePrism.prism(
            s -> Try(() -> Integer.parseInt(s)).toEither().mapLeft(Function1.constant(s)),
            i -> Integer.toString(i)
    );

    @Test
    public void testBasic() {
        assertEquals("3", View.view(Re.re(extractInt), 3));
        assertEquals(Either.right(3), Matching.matching(extractInt, "3"));
        assertEquals(Either.left("bazinga"), Matching.matching(extractInt, "bazinga"));
    }
}
