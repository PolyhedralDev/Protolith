package com.dfsek.protolith.optics;

import com.dfsek.protolith.functor.Functor;
import com.dfsek.protolith.functor.Profunctor;
import com.dfsek.protolith.functor.profunctors.Cartesian;
import com.dfsek.protolith.monad.Monad;
import com.dfsek.protolith.optics.interact.Set;
import com.dfsek.protolith.optics.interact.View;
import io.vavr.CheckedFunction2;
import io.vavr.Function2;
import io.vavr.Tuple2;

import java.util.function.BiFunction;
import java.util.function.Function;

@FunctionalInterface
public interface Lens<S, T, A, B> extends
        Optic<Cartesian<?, ?, ?>, Functor<?, ?>, S, T, A, B>,
        Monad<T, Lens<S, ?, A, B>>,
        Profunctor<S, T, Lens<?, ?, A, B>> {
    static <S, T, A, B> Lens<S, T, A, B> lens(Function<? super S, ? extends A> getter,
                                              Function2<? super S, ? super B, ? extends T> setter) {
        return lens(Optic.<Cartesian<?, ?, ?>, Functor<?, ?>,
                S, T, A, B,
                Functor<B, ? extends Functor<?, ?>>,
                Functor<T, ? extends Functor<?, ?>>,
                Cartesian<A, Functor<B, ? extends Functor<?, ?>>, ? extends Cartesian<?, ?, ?>>,
                Cartesian<S, Functor<T, ? extends Functor<?, ?>>, ? extends Cartesian<?, ?, ?>>>optic(
                afb -> afb.<S>cartesian().diMap(s -> new Tuple2<>(s, getter.apply(s)),
                        tuple -> tuple.apply((s, fb) -> fb.map(setter.apply(s))))));
    }
    static <S, T, A, B> Lens<S, T, A, B> lens(
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
    default <R> Lens<R, T, A, B> mapLeft(Function<? super R, ? extends S> fn) {
        return (Lens<R, T, A, B>) Profunctor.super.<R>mapLeft(fn);
    }

    @Override
    default <U> Lens<S, U, A, B> mapRight(Function<? super T, ? extends U> fn) {
        return (Lens<S, U, A, B>) Profunctor.super.<U>mapRight(fn);
    }

    @Override
    default <R, U> Lens<R, U, A, B> diMap(Function<? super R, ? extends S> lFn, Function<? super T, ? extends U> rFn) {
        return this.<R>mapLeft(lFn).mapRight(rFn);
    }

    @Override
    default <U> Lens<S, U, A, B> flatMap(Function<? super T, ? extends Monad<U, Lens<S, ?, A, B>>> f) {
        return lens(View.view(this), (s, b) -> Set.set(f.apply(Set.set(this, b, s)).<Lens<S, U, A, B>>coerce(), b, s));
    }

    @Override
    default <U> Lens<S, U, A, B> pure(U u) {
        return lens(View.view(this), (s, b) -> u);
    }
}
