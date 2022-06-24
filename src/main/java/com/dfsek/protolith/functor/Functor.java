package com.dfsek.protolith.functor;

import java.util.function.Function;

/**
 * Represents a type that may be mapped.
 *
 * @param <A> Type to be mapped over
 * @param <F> Functor type
 */
@FunctionalInterface
public interface Functor<A, F extends Functor<?, F>> {
    /**
     * Map the value of this functor to a new value.
     *
     * @param f   Mapping function.
     * @param <B> Type of new value.
     * @return New instance of this functor containing mapped value.
     */
    <B> Functor<B, F> map(Function<? super A, ? extends B> f);

    default <C extends Functor<A, F>> C coerce() {
        //noinspection unchecked
        return (C) this;
    }
}
