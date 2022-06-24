package com.dfsek.protolith.monad;

import com.dfsek.protolith.functor.Applicative;
import io.vavr.Function1;

public interface Monad<A, M extends Monad<?, M>> extends Applicative<A, M> {
    <B> Monad<B, M> flatMap(Function1<? super A, ? extends Monad<B, M>> map);

    @Override
    <B> Monad<B, M> pure(B b);

    @Override
    default <B> Monad<B, M> zip(Applicative<Function1<? super A, ? extends B>, M> appFn) {
        return flatMap(a -> appFn.<Monad<Function1<? super A, ? extends B>, M>>coerce().map(f -> f.apply(a)));
    }

    @Override
    default <B> Monad<B, M> map(Function1<? super A, ? extends B> fn) {
        return flatMap(a -> pure(fn.apply(a)));
    }
}
