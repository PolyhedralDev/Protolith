package com.dfsek.protolith.optics.interact;

import com.dfsek.protolith.functor.Profunctor;
import com.dfsek.protolith.functor.function.Fun;
import com.dfsek.protolith.functor.functors.Const;
import com.dfsek.protolith.functor.functors.Identity;
import com.dfsek.protolith.functor.functors.Tagged;
import com.dfsek.protolith.optics.Optic;
import io.vavr.Function1;

/**
 * Flip an optic around
 */
public final class Re<S, T, A, B> implements
        Function1<Optic<? super Tagged<?, ?>, ? super Identity<?>, S, T, A, B>,
                Optic<Profunctor<?, ?, ?>, Const<T, ?>, B, A, T, S>> {

    private static final Re<?, ?, ?, ?> INSTANCE = new Re<>();

    private Re() {
    }

    @Override
    public Optic<Profunctor<?, ?, ?>, Const<T, ?>, B, A, T, S> apply(
            Optic<? super Tagged<?, ?>, ? super Identity<?>, S, T, A, B> optic) {
        return Optic.<Profunctor<?, ?, ?>, Const<T, ?>, B, A, T, S,
                Const<T, S>, Const<T, A>,
                Profunctor<T, Const<T, S>, ? extends Profunctor<?, ?, ?>>,
                Profunctor<B, Const<T, A>, ? extends Profunctor<?, ?, ?>>>optic(
                pafb -> pafb.diMap(
                        (Function1<B, T>) b -> optic.<Tagged<?, ?>, Identity<?>, Identity<B>, Identity<T>,
                                Tagged<A, Identity<B>>, Tagged<S, Identity<T>>>apply(new Tagged<>(new Identity<>(b))).get().get(),
                        ttConst -> new Const<>(ttConst.get())));
    }

    @SuppressWarnings("unchecked")
    public static <S, T, A, B> Re<S, T, A, B> re() {
        return (Re<S, T, A, B>) INSTANCE;
    }

    public static <S, T, A, B> Optic<Profunctor<?, ?, ?>, Const<T, ?>, B, A, T, S> re(
            Optic<? super Tagged<?, ?>, ? super Identity<?>, S, T, A, B> optic) {
        return Re.<S, T, A, B>re().apply(optic);
    }
}