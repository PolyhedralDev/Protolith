package com.dfsek.protolith.optics;

import com.dfsek.protolith.functor.Functor;
import com.dfsek.protolith.functor.Profunctor;
import com.dfsek.protolith.functor.profunctors.Cartesian;
import com.dfsek.protolith.monad.Monad;
import com.dfsek.protolith.optics.interact.View;

import java.util.function.Function;

@FunctionalInterface
public interface Lens<S, T, A, B> extends
        Optic<Cartesian<?, ?, ?>, Functor<?, ?>, S, T, A, B>,
        Monad<T, Lens<S, ?, A, B>>,
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

    @Override
    default <U> Lens<S, U, A, B> flatMap(Function<? super T, ? extends Monad<U, Lens<S, ?, A, B>>> f) {
        return lens(View.view(this), (s, b) -> set(f.apply(set(this, b, s)).<Lens<S, U, A, B>>coerce(), b, s));
    }

    @Override
    default <U> Lens<S, U, A, B> pure(U u) {
        return lens(View.view(this), (s, b) -> u);
    }
}
