package com.dfsek.protolith.functor;


import io.vavr.Function1;

@FunctionalInterface
public interface Profunctor<A, B, P extends Profunctor<?, ?, P>> extends Contravariant<A, Profunctor<?, B, P>> {
    <Z, C> Profunctor<Z, C, P> diMap(Function1<? super Z, ? extends A> lFn, Function1<? super B, ? extends C> rFn);

    default <Z> Profunctor<Z, B, P> mapLeft(Function1<? super Z, ? extends A> fn) {
        return diMap(fn, Function1.identity());
    }

    default <C> Profunctor<A, C, P> mapRight(Function1<? super B, ? extends C> fn) {
        return diMap(Function1.identity(), fn);
    }

    @Override
    default <Z> Profunctor<Z, B, P> contraMap(Function1<? super Z, ? extends A> fn) {
        return mapLeft(fn);
    }
}
