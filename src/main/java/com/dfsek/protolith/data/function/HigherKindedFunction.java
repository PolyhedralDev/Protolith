package com.dfsek.protolith.data.function;

import com.dfsek.protolith.kind.Kind2;
import io.vavr.Function1;

import java.util.function.Function;

public interface HigherKindedFunction<A, B> extends Kind2<HigherKindedFunction<A, B>, A, B>, Function1<A, B> {
    @Override
    default HigherKindedFunction<A, B> get() {
        return this;
    }

    static <A1, B1> HigherKindedFunction<A1, B1> of(Function<A1, B1> f) {
        return f::apply;
    }

    @SuppressWarnings("unchecked")
    static <A1, B1> Function<A1, B1> of(Kind2<HigherKindedFunction<?, ?>, A1, B1> f) {
        return (Function<A1, B1>) f.get();
    }

    static Profunctor<HigherKindedFunction<?, ?>> profunctor() {
        return new Profunctor<>() {
            @SuppressWarnings("unchecked")
            @Override
            public <A, B, C, D> Kind2<HigherKindedFunction<?, ?>, C, D> dimap(Kind2<HigherKindedFunction<?, ?>, A, B> h, Function1<C, A> f, Function1<B, D> g) {
                return (Kind2<HigherKindedFunction<?,?>, C, D>) (Object) of(g.compose(HigherKindedFunction.<A, B>of(h).compose(f)));
            }
        };
    }
}
