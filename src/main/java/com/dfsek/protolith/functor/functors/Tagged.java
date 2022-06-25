package com.dfsek.protolith.functor.functors;

import com.dfsek.protolith.functor.profunctors.Cocartesian;
import com.dfsek.protolith.monad.Monad;
import io.vavr.Function1;
import io.vavr.control.Either;

import java.util.function.Supplier;

public record Tagged<S, B>(B value) implements
        Monad<B, Tagged<S, ?>>,
        Cocartesian<S, B, Tagged<?, ?>> ,
        Supplier<B> {

    @Override
    public <C> Cocartesian<Either<C, S>, Either<C, B>, Tagged<?, ?>> cocartesian() {
        return new Tagged<>(Either.right(value));
    }

    @Override
    public <Z, C> Cocartesian<Z, C, Tagged<?, ?>> diMap(Function1<? super Z, ? extends S> lFn, Function1<? super B, ? extends C> rFn) {
        return new Tagged<>(rFn.apply(value));
    }

    @Override
    public <B1> Monad<B1, Tagged<S, ?>> flatMap(Function1<? super B, ? extends Monad<B1, Tagged<S, ?>>> map) {
        return map.apply(value).coerce();
    }

    @Override
    public <B1> Monad<B1, Tagged<S, ?>> pure(B1 b1) {
        return new Tagged<>(b1);
    }

    @Override
    public B get() {
        return value;
    }
}
