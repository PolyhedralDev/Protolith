package com.dfsek.protolith.functor.functors;

import com.dfsek.protolith.functor.Bifunctor;

import java.util.function.Function;
import java.util.function.Supplier;

public record Const<A, B>(A value) implements
        Bifunctor<A, B, Const<?, ?>>,
        Supplier<A> {
    @Override
    public <C, D> Bifunctor<C, D, Const<?, ?>> biMap(Function<? super A, ? extends C> left, Function<? super B, ? extends D> right) {
        return new Const<>(left.apply(value));
    }

    @Override
    public A get() {
        return value;
    }
}
