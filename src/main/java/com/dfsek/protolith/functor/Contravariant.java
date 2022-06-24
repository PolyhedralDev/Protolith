package com.dfsek.protolith.functor;

import io.vavr.Function1;

@FunctionalInterface
public interface Contravariant<A, C extends Contravariant<?, C>> {
    <B> Contravariant<B, C> contraMap(Function1<? super B, ? extends A> fn);
}
