package com.dfsek.protolith.data.optics;

import com.dfsek.protolith.data.function.HigherKindedFunction;
import com.dfsek.protolith.data.function.Profunctor;
import io.vavr.Function1;

public interface Optic<A, B, S, T> {
    <K> ProfunctorOptic<K, A, B, S, T> apply(Profunctor<K> p);
    default Function1<S, T> apply(final Function1<A, B> f) {
        return apply(HigherKindedFunction.profunctor()).apply(HigherKindedFunction.of(f)).unkind();
    }

    default <C, D> Optic<C, D, S, T> compose(Optic<C, D, A, B> f) {
        return new Optic<>() {
            @Override
            public <P> ProfunctorOptic<P, C, D, S, T> apply(final Profunctor<P> p) {
                return Optic.this.apply(p).compose(f.apply(p));
            }
        };
    }
}
