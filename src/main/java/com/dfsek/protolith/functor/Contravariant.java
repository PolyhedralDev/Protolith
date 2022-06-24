package com.dfsek.protolith.functor;

import java.util.function.Function;

@FunctionalInterface
public interface Contravariant<A, C extends Contravariant<?, C>> {
    <B> Contravariant<B, C> contraMap(Function<? super B, ? extends A> fn);
}
