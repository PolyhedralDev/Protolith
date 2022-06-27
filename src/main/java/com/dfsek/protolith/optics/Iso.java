package com.dfsek.protolith.optics;

import com.dfsek.protolith.functor.Profunctor;
import io.vavr.Function1;

public record Iso<S, T, A, B>(Function1<S, A> from, Function1<B, T> to) implements Optic<Profunctor<?, ?, ?>, S, T, A, B> {
    @SuppressWarnings("unchecked")
    @Override
    public <PN extends Profunctor<?, ?, ? extends Profunctor<?, ?, ?>>,
            PAB extends Profunctor<A, B, ? extends PN>,
            PST extends Profunctor<S, T, ? extends PN>> PST apply(PAB pab) {
        return (PST) pab.diMap(from, to);
    }
}
