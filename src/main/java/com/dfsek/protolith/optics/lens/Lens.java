package com.dfsek.protolith.optics.lens;

import com.dfsek.protolith.functor.Applicative;
import com.dfsek.protolith.functor.Functor;
import com.dfsek.protolith.functor.Profunctor;
import com.dfsek.protolith.functor.profunctors.Cartesian;
import com.dfsek.protolith.monad.Monad;
import com.dfsek.protolith.optics.Optic;
import com.dfsek.protolith.optics.interact.Set;
import com.dfsek.protolith.optics.interact.View;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Tuple2;

@FunctionalInterface
public interface Lens<S, T, A, B> extends
        Optic<Cartesian<?, ?, ?>, Functor<?, ?>, S, T, A, B>,
        Monad<T, Lens<S, ?, A, B>>,
        Profunctor<S, T, Lens<?, ?, A, B>> {
    static <S, T, A, B> Lens<S, T, A, B> lens(Function1<? super S, ? extends A> getter,
                                              Function2<? super S, ? super B, ? extends T> setter) {
        return adapt(Optic.<Cartesian<?, ?, ?>, Functor<?, ?>,
                S, T, A, B,
                Functor<B, ? extends Functor<?, ?>>,
                Functor<T, ? extends Functor<?, ?>>,
                Cartesian<A, Functor<B, ? extends Functor<?, ?>>, ? extends Cartesian<?, ?, ?>>,
                Cartesian<S, Functor<T, ? extends Functor<?, ?>>, ? extends Cartesian<?, ?, ?>>>optic(
                afb -> afb.<S>cartesian().diMap(s -> new Tuple2<>(s, getter.apply(s)),
                        tuple -> tuple.apply((s, fb) -> fb.map(setter.apply(s))))));
    }
    static <S, T, A, B> Lens<S, T, A, B> adapt(
            Optic<? super Cartesian<?, ?, ?>, ? super Functor<?, ?>, S, T, A, B> optic) {
        return new Lens<>() {
            @Override
            public <CoP extends Profunctor<?, ?, ? extends Cartesian<?, ?, ?>>,
                    CoF extends Functor<?, ? extends Functor<?, ?>>,
                    FB extends Functor<B, ? extends CoF>,
                    FT extends Functor<T, ? extends CoF>,
                    PAFB extends Profunctor<A, FB, ? extends CoP>,
                    PSFT extends Profunctor<S, FT, ? extends CoP>> PSFT apply(PAFB pafb) {
                return optic.apply(pafb);
            }
        };
    }

    @Override
    default <R> Lens<R, T, A, B> mapLeft(Function1<? super R, ? extends S> fn) {
        return (Lens<R, T, A, B>) Profunctor.super.<R>mapLeft(fn);
    }

    @Override
    default <U> Lens<S, U, A, B> mapRight(Function1<? super T, ? extends U> fn) {
        return (Lens<S, U, A, B>) Profunctor.super.<U>mapRight(fn);
    }

    @Override
    default <R, U> Lens<R, U, A, B> diMap(Function1<? super R, ? extends S> lFn, Function1<? super T, ? extends U> rFn) {
        return this.<R>mapLeft(lFn).mapRight(rFn);
    }

    @Override
    default <U> Lens<S, U, A, B> flatMap(Function1<? super T, ? extends Monad<U, Lens<S, ?, A, B>>> f) {
        return lens(View.view(this), (s, b) -> Set.set(f.apply(Set.set(this, b, s)).<Lens<S, U, A, B>>coerce(), b, s));
    }

    @Override
    default <U> Lens<S, U, A, B> pure(U u) {
        return lens(View.view(this), (s, b) -> u);
    }

    @Override
    default <U, V> Lens<U, V, A, B> compose(Optic<? super Cartesian<?, ?, ?>, ? super Functor<?, ?>, U, V, S, T> g) {
        return adapt(Optic.super.compose(g));
    }

    @Override
    default <R> Lens<R, T, A, B> mapS(Function1<? super R, ? extends S> fn) {
        return adapt(Optic.super.mapS(fn));
    }

    @Override
    default <U> Lens<S, U, A, B> mapT(Function1<? super T, ? extends U> fn) {
        return adapt(Optic.super.mapT(fn));
    }

    @Override
    default <C> Lens<S, T, C, B> mapA(Function1<? super A, ? extends C> fn) {
        return adapt(Optic.super.mapA(fn));
    }

    @Override
    default <Z> Lens<S, T, A, Z> mapB(Function1<? super Z, ? extends B> fn) {
        return adapt(Optic.super.mapB(fn));
    }
}
