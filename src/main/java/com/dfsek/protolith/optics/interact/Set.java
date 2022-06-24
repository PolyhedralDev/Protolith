package com.dfsek.protolith.optics.interact;

import com.dfsek.protolith.functor.function.Fun;
import com.dfsek.protolith.functor.functors.Identity;
import com.dfsek.protolith.optics.Optic;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Function3;

import java.util.function.Function;

public final class Set<S, T, A, B> implements Function3<Optic<? super Fun<?, ?>, ? super Identity<?>, S, T, A, B>, B, S, T> {

    private static final Set<?, ?, ?, ?> INSTANCE = new Set<>();

    private Set() {
    }

    @Override
    public T apply(Optic<? super Fun<?, ?>, ? super Identity<?>, S, T, A, B> optic, B b, S s) {
        return Over.over(optic, Function1.constant(b), s);
    }

    @SuppressWarnings("unchecked")
    public static <S, T, A, B> Set<S, T, A, B> set() {
        return (Set<S, T, A, B>) INSTANCE;
    }

    public static <S, T, A, B> Function2<B, S, T> set(Optic<? super Fun<?, ?>, ? super Identity<?>, S, T, A, B> optic) {
        return Set.<S, T, A, B>set().apply(optic);
    }

    public static <S, T, A, B> Function<S, T> set(Optic<? super Fun<?, ?>, ? super Identity<?>, S, T, A, B> optic, B b) {
        return set(optic).apply(b);
    }

    public static <S, T, A, B> T set(Optic<? super Fun<?, ?>, ? super Identity<?>, S, T, A, B> optic, B b, S s) {
        return set(optic, b).apply(s);
    }
}
