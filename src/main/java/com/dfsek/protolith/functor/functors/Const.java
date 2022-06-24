package com.dfsek.protolith.functor.functors;

import com.dfsek.protolith.functor.Bifunctor;
import com.dfsek.protolith.monad.Monad;

import java.util.function.Function;
import java.util.function.Supplier;

public record Const<A, B>(A value) implements
        Monad<B, Const<A, ?>>,
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

    @Override
    @SuppressWarnings("unchecked")
    public <B1> Monad<B1, Const<A, ?>> flatMap(Function<? super B, ? extends Monad<B1, Const<A, ?>>> map) {
        return (Const<A, B1>) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <B1> Monad<B1, Const<A, ?>> pure(B1 b1) {
        return (Const<A, B1>) this;
    }
}
