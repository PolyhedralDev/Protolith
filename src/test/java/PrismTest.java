/*
import com.dfsek.protolith.optics.prism.Prisms;
import com.dfsek.protolith.optics.prism.SimplePrism;
import io.vavr.Function1;
import io.vavr.control.Either;
import io.vavr.control.Option;
import org.junit.jupiter.api.Test;

import static com.dfsek.protolith.DSL.*;
import static io.vavr.API.Try;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrismTest {
    private static final SimplePrism<String, Integer> extractInt = simplePrism(
            s -> Try(() -> Integer.parseInt(s)).toEither().mapLeft(Function1.constant(s)),
            i -> Integer.toString(i)
    );

    @Test
    public void testBasic() {
        assertEquals("3", view(re(extractInt), 3));
        assertEquals(Either.right(3), matching(extractInt, "3"));
        assertEquals(Either.left("bazinga"), matching(extractInt, "bazinga"));
    }

    @Test
    public void testComposition() {
        SimplePrism<Option<String>, Integer> optionalString = simplePrism(extractInt.compose(Prisms.optionValue()));
        assertEquals(Either.right(3), matching(optionalString, Option.of("3")));
        assertEquals(Either.left(Option.of("bazinga")), matching(optionalString, Option.of("bazinga")));
        assertEquals(Either.left(Option.none()), matching(optionalString, Option.none()));
    }

    @Test
    public void testLensComposition() {

    }
}
*/