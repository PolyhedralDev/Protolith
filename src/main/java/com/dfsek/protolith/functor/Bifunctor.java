package com.dfsek.protolith.functor;

import java.util.function.Function;

public interface Bifunctor<A, B, BF extends Bifunctor<?, ?, BF>> {
    default <C> Bifunctor<C, B, BF> mapLeft(Function<? super A, ? extends C> left) {
        return biMap(left, Function.identity());
    }

    default <C> Bifunctor<A, C, BF> mapRight(Function<? super B, ? extends C> right) {
        return biMap(Function.identity(), right);
    }

    <C, D> Bifunctor<C, D, BF> biMap(Function<? super A, ? extends C> left, Function<? super B, ? extends D> right);
}
