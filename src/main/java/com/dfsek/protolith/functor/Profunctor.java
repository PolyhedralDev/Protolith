package com.dfsek.protolith.functor;

import java.util.function.Function;

@FunctionalInterface
public interface Profunctor<A, B, P extends Profunctor<?, ?, P>> extends Contravariant<A, Profunctor<?, B, P>> {
    <Z, C> Profunctor<Z, C, P> diMap(Function<? super Z, ? extends A> lFn, Function<? super B, ? extends C> rFn);

    default <Z> Profunctor<Z, B, P> mapLeft(Function<? super Z, ? extends A> fn) {
        return diMap(fn, Function.identity());
    }

    default <C> Profunctor<A, C, P> mapRight(Function<? super B, ? extends C> fn) {
        return diMap(Function.identity(), fn);
    }

    @Override
    default <Z> Profunctor<Z, B, P> contraMap(Function<? super Z, ? extends A> fn) {
        return mapLeft(fn);
    }
}
