package com.dfsek.protolith.optics;

import com.dfsek.protolith.functor.Functor;
import com.dfsek.protolith.functor.Profunctor;

import java.util.function.Function;

public interface Optic<P extends Profunctor<?, ?, ? extends P>, F extends Functor<?, ? extends F>, S, T, A, B> {
    <PN extends Profunctor<?, ?, ? extends P>,
            FN extends Functor<?, ? extends F>,
            FB extends Functor<B, ? extends FN>,
            FT extends Functor<T, ? extends FN>,
            PAFB extends Profunctor<A, FB, ? extends PN>,
            PSFT extends Profunctor<S, FT, ? extends PN>> PSFT apply(PAFB pafb);

}
