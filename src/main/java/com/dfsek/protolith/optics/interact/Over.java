package com.dfsek.protolith.optics.interact;

import com.dfsek.protolith.functor.function.Fun;
import com.dfsek.protolith.functor.functors.Identity;
import com.dfsek.protolith.optics.Optic;
import io.vavr.Function2;
import io.vavr.Function3;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class Over<S, T, A, B> implements
        Function3<Optic<? super Fun<?, ?>, ? super Identity<?>, S, T, A, B>, Function<? super A, ? extends B>, S, T> {

    private static final Over<?, ?, ?, ?> INSTANCE = new Over<>();

    private Over() {
    }

    @Override
    public T apply(Optic<? super Fun<?, ?>, ? super Identity<?>, S, T, A, B> optic,
                          Function<? super A, ? extends B> fn,
                          S s) {
        return optic.<Fun<?, ?>, Identity<?>, Identity<B>, Identity<T>, Fun<A, Identity<B>>, Fun<S, Identity<T>>>apply(
                a -> new Identity<>(fn.apply(a))).apply(s).get();
    }

    @SuppressWarnings("unchecked")
    public static <S, T, A, B> Over<S, T, A, B> over() {
        return (Over<S, T, A, B>) INSTANCE;
    }

    public static <S, T, A, B> Function2<Function<? super A, ? extends B>, S, T> over(
            Optic<? super Fun<?, ?>, ? super Identity<?>, S, T, A, B> optic) {
        return Over.<S, T, A, B>over().apply(optic);
    }

    public static <S, T, A, B> Function<S, T> over(Optic<? super Fun<?, ?>, ? super Identity<?>, S, T, A, B> optic,
                                              Function<? super A, ? extends B> fn) {
        return over(optic).apply(fn);
    }

    public static <S, T, A, B> T over(Optic<? super Fun<?, ?>, ? super Identity<?>, S, T, A, B> optic,
                                      Function<? super A, ? extends B> fn, S s) {
        return over(optic, fn).apply(s);
    }
}