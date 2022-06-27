package com.dfsek.protolith.optics;

import com.dfsek.protolith.functor.Functor;
import com.dfsek.protolith.functor.Profunctor;
import com.dfsek.protolith.monad.Monad;
import com.dfsek.protolith.optics.interact.Re;
import com.dfsek.protolith.optics.interact.View;
import io.vavr.Function1;

import static com.dfsek.protolith.DSL.re;
import static com.dfsek.protolith.DSL.view;

public interface Iso<S, T, A, B> extends
        Optic<Profunctor<?, ?, ?>,
                Functor<?, ?>,
                S, T, A, B>,
        Monad<T, Iso<S, ?, A, B>>,
        Profunctor<S, T, Iso<?, ?, A, B>> {

    static <P extends Profunctor<?, ?, ? extends P>,
            S, T, A, B,
            F extends Functor<?, F>,
            FB extends Functor<B, F>,
            FT extends Functor<T, F>,
            PAFB extends Profunctor<A, FB, ? extends P>,
            PSFT extends Profunctor<S, FT, ? extends P>> Iso<S, T, A, B> iso(Function1<PAFB, PSFT> fn) {
        return new Iso<>() {

            @SuppressWarnings("unchecked")
            @Override
            public <PN extends Profunctor<?, ?, ? extends Profunctor<?, ?, ?>>,
                    FN extends Functor<?, ? extends Functor<?, ?>>,
                    CoFB extends Functor<B, ? extends FN>,
                    CoFT extends Functor<T, ? extends FN>,
                    CoPAFB extends Profunctor<A, CoFB, ? extends PN>,
                    CoPSFT extends Profunctor<S, CoFT, ? extends PN>> CoPSFT apply(CoPAFB pafb) {
                return (CoPSFT) fn.apply((PAFB) pafb);
            }
        };
    }

    static <S, T, A, B> Iso<S, T, A, B> iso(Function1<? super S, ? extends A> to,
                                            Function1<? super B, ? extends T> from) {
        return adapt(Optic.<Profunctor<?, ?, ?>,
                Functor<?, ?>,
                S, T, A, B,
                Functor<B, ? extends Functor<?, ?>>,
                Functor<T, ? extends Functor<?, ?>>,
                Profunctor<A, Functor<B, ? extends Functor<?, ?>>, ? extends Profunctor<?, ?, ?>>,
                Profunctor<S, Functor<T, ? extends Functor<?, ?>>, ? extends Profunctor<?, ?, ?>>>
                optic(abProfunctor -> abProfunctor.diMap(to, bFunctor -> bFunctor.map(from))));
    }

    static <S, T, A, B> Iso<S, T, A, B> adapt(
            Optic<? super Profunctor<?, ?, ?>,? super Functor<?, ?>, S, T, A, B> optic) {
        return new Iso<>() {

            @Override
            public <PN extends Profunctor<?, ?, ? extends Profunctor<?, ?, ?>>,
                    FN extends Functor<?, ? extends Functor<?, ?>>,
                    FB extends Functor<B, ? extends FN>,
                    FT extends Functor<T, ? extends FN>,
                    PAFB extends Profunctor<A, FB, ? extends PN>,
                    PSFT extends Profunctor<S, FT, ? extends PN>> PSFT apply(PAFB pafb) {
                return optic.apply(pafb);
            }
        };
    }

    default Function1<? super S, ? extends A> to() {
        return b -> View.view(this, b);
    }

    default Function1<? super B, ? extends T> from() {
        return b -> View.view(Re.re(this), b);
    }

    @Override
    default <R> Iso<R, T, A, B> mapLeft(Function1<? super R, ? extends S> fn) {
        return (Iso<R, T, A, B>) Profunctor.super.<R>mapLeft(fn);
    }

    @Override
    default <U> Iso<S, U, A, B> mapRight(Function1<? super T, ? extends U> fn) {
        return (Iso<S, U, A, B>) Profunctor.super.<U>mapRight(fn);
    }

    @Override
    default <R, U> Iso<R, U, A, B> diMap(Function1<? super R, ? extends S> lFn, Function1<? super T, ? extends U> rFn) {
        return this.<R>mapLeft(lFn).mapRight(rFn);
    }

    @Override
    default <U> Iso<S, U, A, B> flatMap(Function1<? super T, ? extends Monad<U, Iso<S, ?, A, B>>> f) {
        return iso(View.view(this), (Function1<B, U>) b -> view(re(f.apply(from().apply(b)).<Iso<S, U, A, B>>coerce()), b));
    }

    @Override
    default <U> Iso<S, U, A, B> pure(U u) {
        return iso(to(), Function1.constant(u));
    }

    @Override
    default <U, V> Iso<U, V, A, B> compose(Optic<? super Profunctor<?, ?, ?>, ? super Functor<?, ?>, U, V, S, T> g) {
        return (Iso<U, V, A, B>) Optic.super.compose(g);
    }

    @Override
    default <R> Iso<R, T, A, B> mapS(Function1<? super R, ? extends S> fn) {
        return (Iso<R, T, A, B>) Optic.super.mapS(fn);
    }

    @Override
    default <U> Iso<S, U, A, B> mapT(Function1<? super T, ? extends U> fn) {
        return (Iso<S, U, A, B>) Optic.super.mapT(fn);
    }

    @Override
    default <C> Iso<S, T, C, B> mapA(Function1<? super A, ? extends C> fn) {
        return (Iso<S, T, C, B>) Optic.super.mapA(fn);
    }

    @Override
    default <Z> Iso<S, T, A, Z> mapB(Function1<? super Z, ? extends B> fn) {
        return (Iso<S, T, A, Z>) Optic.super.mapB(fn);
    }
}
