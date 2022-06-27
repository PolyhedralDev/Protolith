package com.dfsek.protolith.optics.prism;

import io.vavr.Function1;
import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Option;

public final class Prisms {
    public static <T> SimplePrism<Option<T>, T> optionValue() {
        return SimplePrism.prism(ts -> ts.toEither(ts), Option::of);
    }
}
