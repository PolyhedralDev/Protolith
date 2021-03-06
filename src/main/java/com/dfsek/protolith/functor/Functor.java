package com.dfsek.protolith.functor;

import io.vavr.Function1;

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
    <B> Functor<B, F> map(Function1<? super A, ? extends B> f);

    @SuppressWarnings("unchecked")
    default <C extends Functor<A, F>> C coerce() {
        return (C) this;
    }
}
