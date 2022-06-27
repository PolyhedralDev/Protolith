package com.dfsek.protolith.optics.interact;

import com.dfsek.protolith.functor.function.Fun;
import com.dfsek.protolith.functor.functors.Const;
import com.dfsek.protolith.optics.Optic;
import io.vavr.Function1;
import io.vavr.Function2;

public final class View<S, T, A, B> implements Function2<Optic<? super Fun<?, ?>, S, T, A, B>, S, A> {
    private static final View<?, ?, ?, ?> INSTANCE = new View<>();
    private View() {

    }

    @SuppressWarnings("unchecked")
    public static <S, T, A, B> View<S, T, A, B> view() {
        return (View<S, T, A, B>) INSTANCE;
    }

    public static <S, T, A, B> Function1<S, A> view(Optic<? super Fun<?, ?>, S, T, A, B> optic) {
        return View.<S, T, A, B>view().apply(optic);
    }

    public static <S, T, A, B> A view(Optic<? super Fun<?, ?>, S, T, A, B> optic, S s) {
        return view(optic).apply(s);
    }

    @Override
    public A apply(Optic<? super Fun<?, ?>, S, T, A, B> optic, S context) {
        return optic.<Fun<?, ?>, Const<A, B>, Const<A, T>, Fun<A, Const<A, B>>, Fun<S, Const<A, T>>>apply(
                Const::new).apply(context).get();
    }
}
