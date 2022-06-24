package com.dfsek.protolith.functor;

import java.util.function.Function;

public interface Applicative<A, F extends Applicative<?, F>> extends Functor<A, F>{
    <B> Applicative<B, F> pure(B b);

    <B> Applicative<B, F> zip(Applicative<Function<? super A, ? extends B>, F> appFn);

    @Override
    default <B> Applicative<B, F> map(Function<? super A, ? extends B> fn) {
        return zip(pure(fn));
    }
}
