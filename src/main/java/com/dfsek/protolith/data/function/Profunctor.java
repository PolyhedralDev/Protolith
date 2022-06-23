package com.dfsek.protolith.data.function;

import com.dfsek.protolith.kind.Kind2;
import io.vavr.Function1;

public interface Profunctor<T> {
    <A, B, C, D> Kind2<T, C, D> dimap(Kind2<T, A, B> h, Function1<C, A> f, Function1<B, D> g);
}
