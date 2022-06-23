package com.dfsek.protolith.data.optics;

import io.vavr.Function1;
import io.vavr.control.Either;

/**
 *
 * @param match
 * @param build
 * @param <A>
 * @param <B>
 * @param <S>
 * @param <T>
 */
public record Prism<A, B, S, T>(
        Function1<S, Either<A, T>> match,
        Function1<B, T> build
) {
    public Either<A, T> match(S context) {
        return match.apply(context);
    }

    public T build(B context) {
        return build.apply(context);
    }
}
