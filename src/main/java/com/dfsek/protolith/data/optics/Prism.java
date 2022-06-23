package com.dfsek.protolith.data.optics;

import io.vavr.Function1;
import io.vavr.control.Either;

/**
 * A prism allows creating a context {@link T} from a component {@link B}, as well as accessing a context's <b>focus</b>,
 * a safe form of downcasting.
 *
 * @param match
 * @param build
 *
 * @param <A>
 * @param <B>
 * @param <S>
 * @param <T>
 */
public record Prism<A, B, S, T>(
        Function1<S, Either<A, T>> match,
        Function1<B, T> build
) implements Optic<A, B, S, T> {
    /**
     * Get the focus of this prism, if applicable on the provided context instance. Otherwise, get the original value.
     * @param context Context instance.
     * @return Either the focus value, or the context instance.
     */
    public Either<A, T> match(S context) {
        return match.apply(context);
    }

    /**
     * Construct an instance of context {@link T} from an instance of component {@link B}.
     * @param component Component instance.
     * @return New context instance.
     */
    public T build(B component) {
        return build.apply(component);
    }
}
