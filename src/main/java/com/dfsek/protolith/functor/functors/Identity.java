package com.dfsek.protolith.functor.functors;

import com.dfsek.protolith.functor.Bifunctor;
import com.dfsek.protolith.monad.Monad;

import java.util.function.Function;
import java.util.function.Supplier;

public record Identity<A>(A value) implements
        Monad<A, Identity<?>>,
        Supplier<A> {

    @Override
    public A get() {
        return value;
    }

    @Override
    public <B> Identity<B> flatMap(Function<? super A, ? extends Monad<B, Identity<?>>> f) {
        return f.apply(get()).coerce();
    }

    @Override
    public <B1> Identity<B1> pure(B1 value) {
        return new Identity<>(value);
    }
}
