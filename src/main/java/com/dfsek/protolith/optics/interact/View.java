package com.dfsek.protolith.optics.interact;

import com.dfsek.protolith.functor.function.Fun;
import com.dfsek.protolith.functor.functors.Const;
import com.dfsek.protolith.functor.profunctors.Cartesian;
import com.dfsek.protolith.optics.Optic;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class View<S, T, A, B> implements BiFunction<Optic<? super Fun<?, ?>, ? super Const<A, ?>, S, T, A, B>, S, A> {
    private static final View<?, ?, ?, ?> INSTANCE = new View<>();
    private View() {

    }

    @SuppressWarnings("unchecked")
    public static <S, T, A, B> View<S, T, A, B> view() {
        return (View<S, T, A, B>) INSTANCE;
    }

    public static <S, T, A, B> Function<S, A> view(Optic<? super Fun<?, ?>, ? super Const<A, ?>, S, T, A, B> optic) {
        return s -> view(optic, s);
    }


    public static <S, T, A, B> A view(Optic<? super Fun<?, ?>, ? super Const<A, ?>, S, T, A, B> optic, S s) {
        return view(optic).apply(s);
    }

    @Override
    public A apply(Optic<? super Fun<?, ?>, ? super Const<A, ?>, S, T, A, B> optic, S context) {
        return optic.<Fun<?, ?>, Const<A, ?>, Const<A, B>, Const<A, T>, Fun<A, Const<A, B>>, Fun<S, Const<A, T>>>apply(
                Const::new).apply(context).get();
    }
}
