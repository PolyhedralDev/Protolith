package com.dfsek.protolith.functor.functors;

import com.dfsek.protolith.functor.function.Fun;
import com.dfsek.protolith.functor.profunctors.Cocartesian;
import com.dfsek.protolith.monad.Monad;
import io.vavr.Function1;
import io.vavr.control.Either;

public record Market<A, B, S, T>(Function1<? super B, ? extends T> bt,
                                 Function1<? super S, ? extends Either<T, A>> sta) implements
        Monad<T, Market<A, B, S, ?>>,
        Cocartesian<S, T, Market<A, B, ?, ?>> {


    @Override
    public <U> Market<A, B, S, U> pure(U u) {
        return new Market<>(Function1.constant(u), Function1.constant(Either.left(u)));
    }

    @Override
    public <U> Market<A, B, S, U> flatMap(Function1<? super T, ? extends Monad<U, Market<A, B, S, ?>>> f) {
        return new Market<>(b -> f.apply(bt().apply(b)).<Market<A, B, S, U>>coerce().bt().apply(b),
                s -> sta().apply(s).swap()
                        .flatMap(t -> f.apply(t).<Market<A, B, S, U>>coerce().sta()
                                .apply(s).swap()).swap());
    }

    @Override
    public <C> Market<A, B, Either<C, S>, Either<C, T>> cocartesian() {
        return new Market<>(b -> Either.right(bt.apply(b)),
                s -> s.fold(
                        c -> Either.left(Either.left(c)),
                        s1 -> sta.apply(s1).fold(t -> Either.left(Either.right(t)), Either::right)
                )
        );
    }

    @Override
    public <Z, C> Cocartesian<Z, C, Market<A, B, ?, ?>> diMap(Function1<? super Z, ? extends S> lFn, Function1<? super T, ? extends C> rFn) {
        return new Market<>(b -> rFn.apply(bt.apply(b)), z -> sta.apply(lFn.apply(z)).mapLeft(rFn));
    }
}
