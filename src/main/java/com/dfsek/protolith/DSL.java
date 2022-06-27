package com.dfsek.protolith;

import com.dfsek.protolith.functor.Functor;
import com.dfsek.protolith.functor.Profunctor;
import com.dfsek.protolith.functor.function.Fun;
import com.dfsek.protolith.functor.functors.Const;
import com.dfsek.protolith.functor.functors.Identity;
import com.dfsek.protolith.functor.functors.Market;
import com.dfsek.protolith.functor.functors.Tagged;
import com.dfsek.protolith.functor.profunctors.Cartesian;
import com.dfsek.protolith.functor.profunctors.Cocartesian;
import com.dfsek.protolith.optics.Optic;
import com.dfsek.protolith.optics.interact.*;
import com.dfsek.protolith.optics.lens.Lens;
import com.dfsek.protolith.optics.lens.SimpleLens;
import com.dfsek.protolith.optics.prism.Prism;
import com.dfsek.protolith.optics.prism.SimplePrism;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.control.Either;

/**
 * DSL for optic interactions.
 */
public interface DSL {
    static <S, T, A, B> View<S, T, A, B> view() {
        return View.view();
    }

    static <S, T, A, B> Function1<S, A> view(Optic<? super Fun<?, ?>, ? super Const<A, ?>, S, T, A, B> optic) {
        return View.view(optic);
    }

    static <S, T, A, B> A view(Optic<? super Fun<?, ?>, ? super Const<A, ?>, S, T, A, B> optic, S s) {
        return View.view(optic, s);
    }

    static <S, T, A, B> Set<S, T, A, B> set() {
        return Set.set();
    }

    static <S, T, A, B> Function2<B, S, T> set(Optic<? super Fun<?, ?>, ? super Identity<?>, S, T, A, B> optic) {
        return Set.set(optic);
    }

    static <S, T, A, B> Function1<S, T> set(Optic<? super Fun<?, ?>, ? super Identity<?>, S, T, A, B> optic, B b) {
        return Set.set(optic, b);
    }

    static <S, T, A, B> T set(Optic<? super Fun<?, ?>, ? super Identity<?>, S, T, A, B> optic, B b, S s) {
        return Set.set(optic, b, s);
    }

    static <S, T, A, B> Re<S, T, A, B> re() {
        return Re.re();
    }

    static <S, T, A, B> Optic<Profunctor<?, ?, ?>, Const<T, ?>, B, A, T, S> re(Optic<? super Tagged<?, ?>, ? super Identity<?>, S, T, A, B> optic) {
        return Re.re(optic);
    }

    static <S, T, A, B> Over<S, T, A, B> over() {
        return Over.over();
    }

    static <S, T, A, B> Function2<Function1<? super A, ? extends B>, S, T> over(Optic<? super Fun<?, ?>, ? super Identity<?>, S, T, A, B> optic) {
        return Over.over(optic);
    }

    static <S, T, A, B> Function1<S, T> over(Optic<? super Fun<?, ?>, ? super Identity<?>, S, T, A, B> optic, Function1<? super A, ? extends B> fn) {
        return Over.over(optic, fn);
    }

    static <S, T, A, B> T over(Optic<? super Fun<?, ?>, ? super Identity<?>, S, T, A, B> optic, Function1<? super A, ? extends B> fn, S s) {
        return Over.over(optic, fn, s);
    }

    static <S, T, A, B> Matching<S, T, A, B> matching() {
        return Matching.matching();
    }

    static <S, T, A, B> Function1<S, Either<T, A>> matching(Optic<? super Market<A, B, ?, ?>, ? super Identity<?>, S, T, A, B> optic) {
        return Matching.matching(optic);
    }

    static <S, T, A, B> Either<T, A> matching(Optic<? super Market<A, B, ?, ?>, ? super Identity<?>, S, T, A, B> optic, S s) {
        return Matching.matching(optic, s);
    }

    static <S, T, A, B> Lens<S, T, A, B> lens(Function1<? super S, ? extends A> getter,
                                              Function2<? super S, ? super B, ? extends T> setter) {
        return Lens.lens(getter, setter);
    }

    static <S, T, A, B> Lens<S, T, A, B> lens(
            Optic<? super Cartesian<?, ?, ?>, ? super Functor<?, ?>, S, T, A, B> lens) {
        return Lens.adapt(lens);
    }

    static <S, A> SimpleLens<S, A> simpleLens(Function1<? super S, ? extends A> getter,
                                        Function2<? super S, ? super A, ? extends S> setter) {
        return SimpleLens.lens(getter, setter);
    }

    static <S, A> SimpleLens<S, A> simpleLens(
            Optic<? super Cartesian<?, ?, ?>, ? super Functor<?, ?>, S, S, A, A> lens) {
        return SimpleLens.adapt(lens);
    }

    static <S, T, A, B> Prism<S, T, A, B> prism(Optic<? super Cocartesian<?, ?, ?>, ? super Functor<?, ?>, S, T, A, B> prism) {
        return Prism.adapt(prism);
    }

    static <S, T, A, B> Prism<S, T, A, B> prism(Function1<S, Either<T, A>> match, Function1<B, T> build) {
        return Prism.prism(match, build);
    }

    static <S, A> SimplePrism<S, A> simplePrism(Function1<S, Either<S, A>> match, Function1<A, S> build) {
        return SimplePrism.prism(match, build);
    }

    static <S, A> SimplePrism<S, A> simplePrism(
            Optic<? super Cocartesian<?, ?, ?>, ? super Functor<?, ?>, S, S, A, A> prism) {
        return SimplePrism.adapt(prism);
    }
}
