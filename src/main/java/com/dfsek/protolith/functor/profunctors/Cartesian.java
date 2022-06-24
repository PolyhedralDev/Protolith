package com.dfsek.protolith.functor.profunctors;

import com.dfsek.protolith.functor.Profunctor;
import io.vavr.Function1;
import io.vavr.Tuple2;

public interface Cartesian<A, B, P extends Cartesian<?, ?, P>> extends Profunctor<A, B, P> {
    <C> Cartesian<Tuple2<C, A>, Tuple2<C, B>, P> cartesian();

    @Override
    <Z, C> Cartesian<Z, C, P> diMap(Function1<? super Z, ? extends A> lFn, Function1<? super B, ? extends C> rFn);

    @Override
    default <Z> Cartesian<Z, B, P> mapLeft(Function1<? super Z, ? extends A> fn) {
        return (Cartesian<Z, B, P>) Profunctor.super.<Z>mapLeft(fn);
    }

    @Override
    default <C> Cartesian<A, C, P> mapRight(Function1<? super B, ? extends C> fn) {
        return (Cartesian<A, C, P>) Profunctor.super.<C>mapRight(fn);
    }

    @Override
    default <Z> Cartesian<Z, B, P> contraMap(Function1<? super Z, ? extends A> fn) {
        return (Cartesian<Z, B, P>) Profunctor.super.<Z>contraMap(fn);
    }
}
