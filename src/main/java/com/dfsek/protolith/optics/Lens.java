package com.dfsek.protolith.optics;

import com.dfsek.protolith.functor.Profunctor;
import com.dfsek.protolith.functor.profunctors.Cartesian;

import java.util.function.Function;

@FunctionalInterface
public interface Lens<S, T, A, B> extends
        Optic<Cartesian<?, ?, ?>, S, T, A, B>,
        Profunctor<S, T, Lens<?, ?, A, B>> {
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
}
