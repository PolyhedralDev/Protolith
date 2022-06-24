package com.dfsek.protolith.functor;

import io.vavr.Function1;

public interface Bifunctor<A, B, BF extends Bifunctor<?, ?, BF>> {
    default <C> Bifunctor<C, B, BF> mapLeft(Function1<? super A, ? extends C> left) {
        return biMap(left, Function1.identity());
    }

    default <C> Bifunctor<A, C, BF> mapRight(Function1<? super B, ? extends C> right) {
        return biMap(Function1.identity(), right);
    }

    <C, D> Bifunctor<C, D, BF> biMap(Function1<? super A, ? extends C> left, Function1<? super B, ? extends D> right);
}
