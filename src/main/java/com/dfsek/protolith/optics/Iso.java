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
        Optic<Profunctor<?, ?, ?>, S, T, A, B>,
        Monad<T, Iso<S, ?, A, B>>,
        Profunctor<S, T, Iso<?, ?, A, B>> {
    static <S, T, A, B> Iso<S, T, A, B> iso(Function1<? super S, ? extends A> to,
                                              Function1<? super B, ? extends T> from) {
        return adapt(Optic.<Profunctor<?, ?, ?>,
                S, T, A, B,
                Functor<B, ? extends Functor<?, ?>>,
                Functor<T, ? extends Functor<?, ?>>,
                Profunctor<A, Functor<B, ? extends Functor<?, ?>>, ? extends Profunctor<?, ?, ?>>,
                Profunctor<S, Functor<T, ? extends Functor<?, ?>>, ? extends Profunctor<?, ?, ?>>>
                optic(abProfunctor -> abProfunctor.diMap(to, bFunctor -> bFunctor.map(from))));
    }

    static <S, T, A, B> Iso<S, T, A, B> adapt(
            Optic<? super Profunctor<?, ?, ?>, S, T, A, B> optic) {
        return new Iso<>() {

            @Override
            public <PN extends Profunctor<?, ?, ? extends Profunctor<?, ?, ?>>,
                    FB extends Functor<B, ?>,
                    FT extends Functor<T, ?>,
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
}
