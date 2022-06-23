package com.dfsek.protolith.data.optics;

import io.vavr.Function1;

public record Adapter<A, B, S, T>(
        Function1<S, A> from,
        Function1<B, T> to
) implements Optic<A, B, S, T> {
    public A from(S context) {
        return from.apply(context);
    }

    public T to(B component) {
        return to.apply(component);
    }

    @Override
    public Function1<S, T> apply(Function1<A, B> f) {
        return s -> to(f.apply(from(s)));
    }
}
