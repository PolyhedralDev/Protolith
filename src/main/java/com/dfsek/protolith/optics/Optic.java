package com.dfsek.protolith.optics;

import com.dfsek.protolith.functor.Functor;
import com.dfsek.protolith.functor.Profunctor;
import io.vavr.Function1;

public interface Optic<P extends Profunctor<?, ?, ? extends P>, F extends Functor<?, ? extends F>, S, T, A, B> {
    static <P extends Profunctor<?, ?, ? extends P>,
            F extends Functor<?, ? extends F>,
            S, T, A, B,
            FB extends Functor<B, ? extends F>,
            FT extends Functor<T, ? extends F>,
            PAFB extends Profunctor<A, FB, ? extends P>,
            PSFT extends Profunctor<S, FT, ? extends P>> Optic<P, F, S, T, A, B> optic(Function1<PAFB, PSFT> fn) {
        return new Optic<>() {
            @Override
            @SuppressWarnings("unchecked")
            public <CoP extends Profunctor<?, ?, ? extends P>,
                    CoF extends Functor<?, ? extends F>,
                    CoFB extends Functor<B, ? extends CoF>,
                    CoFT extends Functor<T, ? extends CoF>,
                    CoPAFB extends Profunctor<A, CoFB, ? extends CoP>,
                    CoPSFT extends Profunctor<S, CoFT, ? extends CoP>> CoPSFT apply(
                    CoPAFB pafb) {
                return (CoPSFT) fn.apply((PAFB) pafb);
            }
        };
    }
    <PN extends Profunctor<?, ?, ? extends P>,
            FN extends Functor<?, ? extends F>,
            FB extends Functor<B, ? extends FN>,
            FT extends Functor<T, ? extends FN>,
            PAFB extends Profunctor<A, FB, ? extends PN>,
            PSFT extends Profunctor<S, FT, ? extends PN>> PSFT apply(PAFB pafb);

    default <U, V> Optic<P, F, U, V, A, B> compose(Optic<? super P, ? super F, U, V, S, T> g) {
        return new Optic<>() {
            @Override
            public <CoP extends Profunctor<?, ?, ? extends P>,
                    CoF extends Functor<?, ? extends F>,
                    FB extends Functor<B, ? extends CoF>,
                    FU extends Functor<V, ? extends CoF>,
                    PAFB extends Profunctor<A, FB, ? extends CoP>,
                    PRFU extends Profunctor<U, FU, ? extends CoP>>
            PRFU apply(PAFB pafb) {
                return g.<CoP, CoF, Functor<T, ? extends CoF>, FU,
                        Profunctor<S, Functor<T, ? extends CoF>, ? extends CoP>, PRFU>apply(Optic.this.apply(pafb));
            }
        };
    }
}
