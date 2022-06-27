package com.dfsek.protolith.optics;

import com.dfsek.protolith.functor.Profunctor;
import io.vavr.Function1;

public interface Optic<P extends Profunctor<?, ?, ? extends P>, S, T, A, B> {

    static <P extends Profunctor<?, ?, ? extends P>,
            S, T, A, B,
            PAFB extends Profunctor<A, B, ? extends P>,
            PSFT extends Profunctor<S, T, ? extends P>> Optic<P, S, T, A, B> optic(Function1<PAFB, PSFT> fn) {
        return new Optic<>() {
            @Override
            @SuppressWarnings("unchecked")
            public <CoP extends Profunctor<?, ?, ? extends P>,
                    CoPAFB extends Profunctor<A, B, ? extends CoP>,
                    CoPSFT extends Profunctor<S, T, ? extends CoP>> CoPSFT apply(
                    CoPAFB pafb) {
                return (CoPSFT) fn.apply((PAFB) pafb);
            }
        };
    }

    <PN extends Profunctor<?, ?, ? extends P>,
            PAB extends Profunctor<A, B, ? extends PN>,
            PST extends Profunctor<S, T, ? extends PN>> PST apply(PAB pab);

}
