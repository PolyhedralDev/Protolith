package com.dfsek.protolith.functor.function;

import com.dfsek.protolith.functor.profunctors.Cartesian;
import com.dfsek.protolith.monad.Monad;
import io.vavr.Function1;
import io.vavr.Tuple2;

/**
 * Function implementation which extends many protolith interfaces to allow for advanced composition
 */
@FunctionalInterface
public interface Fun<P, R> extends
        Function1<P, R>,
        Monad<R, Fun<P, ?>>,
        Cartesian<P, R, Fun<?, ?>> {

    static <P1, R1> Fun<P1, R1> from(Function1<P1, R1> f) {
        return f::apply;
    }

    @Override
    default <C> Fun<Tuple2<C, P>, Tuple2<C, R>> cartesian() {
        return tuple -> tuple.map2(this);
    }

    @Override
    default <Z, C> Fun<Z, C> diMap(Function1<? super Z, ? extends P> lFn, Function1<? super R, ? extends C> rFn) {
        return z -> rFn.apply(apply(lFn.apply(z)));
    }

    @Override
    default <C> Fun<P, C> flatMap(Function1<? super R, ? extends Monad<C, Fun<P, ?>>> f) {
        return a -> f.apply(apply(a)).<Fun<P, C>>coerce().apply(a);
    }

    @Override
    default <C> Fun<P, C> pure(C c) {
        return ignore -> c;
    }
}
