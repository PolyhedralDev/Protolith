package com.dfsek.protolith.functor.profunctors;

import com.dfsek.protolith.functor.Profunctor;
import io.vavr.Function1;
import io.vavr.control.Either;

public interface Cocartesian<A, B, P extends Cocartesian<?, ?, P>> extends Profunctor<A, B, P> {
    <C> Cocartesian<Either<C, A>, Either<C, B>, P> cocartesian();

    default Cocartesian<A, Either<A, B>, P> choose() {
        return this.<A>cocartesian().contraMap(Either::right);
    }

    @Override
    <Z, C> Cocartesian<Z, C, P> diMap(Function1<? super Z, ? extends A> lFn, Function1<? super B, ? extends C> rFn);
    @Override
    default <Z> Cocartesian<Z, B, P> mapLeft(Function1<? super Z, ? extends A> fn) {
        return (Cocartesian<Z, B, P>) Profunctor.super.<Z>mapLeft(fn);
    }
    @Override
    default <C> Cocartesian<A, C, P> mapRight(Function1<? super B, ? extends C> fn) {
        return (Cocartesian<A, C, P>) Profunctor.super.<C>mapRight(fn);
    }
    @Override
    default <Z> Cocartesian<Z, B, P> contraMap(Function1<? super Z, ? extends A> fn) {
        return (Cocartesian<Z, B, P>) Profunctor.super.<Z>contraMap(fn);
    }
}
