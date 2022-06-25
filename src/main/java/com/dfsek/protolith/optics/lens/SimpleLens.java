package com.dfsek.protolith.optics.lens;

import com.dfsek.protolith.functor.Functor;
import com.dfsek.protolith.functor.Profunctor;
import com.dfsek.protolith.functor.profunctors.Cartesian;
import com.dfsek.protolith.optics.Optic;
import io.vavr.Function1;
import io.vavr.Function2;

public interface SimpleLens<S, A> extends Lens<S, S, A, A> {
    static <S, A> SimpleLens<S, A> lens(Function1<? super S, ? extends A> getter,
                                        Function2<? super S, ? super A, ? extends S> setter) {
        return adapt(Lens.lens(getter, setter));
    }

    static <S, A> SimpleLens<S, A> adapt(
            Optic<? super Cartesian<?, ?, ?>, S, S, A, A> lens) {
        return new SimpleLens<>() {
            @Override
            public <CoP extends Profunctor<?, ?, ? extends Cartesian<?, ?, ?>>,
                    FB extends Functor<A, ?>,
                    FT extends Functor<S, ?>,
                    PAFB extends Profunctor<A, FB, ? extends CoP>,
                    PSFT extends Profunctor<S, FT, ? extends CoP>> PSFT apply(PAFB pafb) {
                return lens.apply(pafb);
            }
        };
    }
}
