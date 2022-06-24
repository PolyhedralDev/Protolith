package com.dfsek.protolith.functor;

import io.vavr.Function1;

public interface Applicative<A, F extends Applicative<?, F>> extends Functor<A, F>{
    <B> Applicative<B, F> pure(B b);

    <B> Applicative<B, F> zip(Applicative<Function1<? super A, ? extends B>, F> appFn);

    @Override
    default <B> Applicative<B, F> map(Function1<? super A, ? extends B> fn) {
        return zip(pure(fn));
    }
}
