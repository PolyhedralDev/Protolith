package com.dfsek.protolith.functor.function;

import com.dfsek.protolith.functor.profunctors.Cartesian;
import com.dfsek.protolith.monad.Monad;
import io.vavr.Tuple2;

import java.util.function.Function;

/**
 * Function implementation which extends many protolith interfaces to allow for advanced composition
 */
@FunctionalInterface
public interface Fun<P, R> extends
        Function<P, R>,
        Monad<R, Fun<P, ?>>,
        Cartesian<P, R, Fun<?, ?>> {

    static <P1, R1> Fun<P1, R1> from(Function<P1, R1> f) {
        return f::apply;
    }

    @Override
    default <C> Fun<Tuple2<C, P>, Tuple2<C, R>> cartesian() {
        return tuple -> tuple.map2(this);
    }

    @Override
    default <Z, C> Fun<Z, C> diMap(Function<? super Z, ? extends P> lFn, Function<? super R, ? extends C> rFn) {
        return z -> rFn.apply(apply(lFn.apply(z)));
    }

    @Override
    default <C> Fun<P, C> flatMap(Function<? super R, ? extends Monad<C, Fun<P, ?>>> f) {
        return a -> f.apply(apply(a)).<Fun<P, C>>coerce().apply(a);
    }

    @Override
    default <C> Fun<P, C> pure(C c) {
        return ignore -> c;
    }
}
