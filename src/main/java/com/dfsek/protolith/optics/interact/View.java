package com.dfsek.protolith.optics.interact;

import com.dfsek.protolith.functor.function.Fun;
import com.dfsek.protolith.functor.functors.Const;
import com.dfsek.protolith.functor.profunctors.Cartesian;
import com.dfsek.protolith.optics.Optic;

import java.util.function.BiFunction;

public final class View<S, T, A, B> implements BiFunction<Optic<? super Fun<?, ?>, ? super Const<A, ?>, S, T, A, B>, S, A> {
    @Override
    public A apply(Optic<? super Fun<?, ?>, ? super Const<A, ?>, S, T, A, B> optic, S context) {
        return optic.<Fun<?, ?>, Const<A, ?>, Const<A, B>, Const<A, T>, Fun<A, Const<A, B>>, Fun<S, Const<A, T>>>apply(
                Const::new).apply(context).get();
    }
}
