package com.dfsek.protolith.optics.prism;

import com.dfsek.protolith.functor.Functor;
import com.dfsek.protolith.functor.Profunctor;
import com.dfsek.protolith.functor.profunctors.Cartesian;
import com.dfsek.protolith.functor.profunctors.Cocartesian;
import com.dfsek.protolith.optics.Optic;
import io.vavr.Function1;
import io.vavr.control.Either;

public interface SimplePrism<S, A> extends Prism<S, S, A, A> {
    static <S, A> SimplePrism<S, A> prism(Function1<S, Either<S, A>> match, Function1<A, S> build) {
        return adapt(Prism.prism(match, build));
    }

    static <S, A> SimplePrism<S, A> adapt(
            Optic<? super Cocartesian<?, ?, ?>, ? super Functor<?, ?>, S, S, A, A> prism) {
        return new SimplePrism<>() {
            @Override
            public <PN extends Profunctor<?, ?, ? extends Cocartesian<?, ?, ?>>,
                    FN extends Functor<?, ? extends Functor<?, ?>>,
                    FB extends Functor<A, ? extends FN>,
                    FT extends Functor<S, ? extends FN>,
                    PAFB extends Profunctor<A, FB, ? extends PN>,
                    PSFT extends Profunctor<S, FT, ? extends PN>> PSFT apply(PAFB pafb) {
                return prism.apply(pafb);
            }
        };
    }
}
