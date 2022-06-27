package com.dfsek.protolith.optics;

import com.dfsek.protolith.functor.Profunctor;
import com.dfsek.protolith.functor.profunctors.Cartesian;
import io.vavr.Tuple2;
import io.vavr.Function1;
import io.vavr.Function2;

public record Lens<S, T, A, B>(Function1<? super S, ? extends A> getter,
                               Function2<? super S, ? super B, ? extends T> setter) implements Optic<Cartesian<?, ?, ?>, S, T, A, B> {
    @Override
    public <PN extends Profunctor<?, ?, ? extends Cartesian<?, ?, ?>>,
            PAB extends Profunctor<A, B, ? extends PN>,
            PST extends Profunctor<S, T, ? extends PN>> PST apply(PAB pab) {
        return Optic.<Cartesian<?, ?, ?>,
                S, T, A, B,
                Cartesian<A, B, ? extends Cartesian<?, ?, ?>>,
                Cartesian<S, T, ? extends Cartesian<?, ?, ?>>>optic(profunctor -> profunctor.<S>cartesian().diMap(s -> new Tuple2<>(s, getter.apply(s)),
                tuple -> tuple.apply(setter))).apply(pab);
    }

    public A get(S context) {
        return getter.apply(context);
    }

    public T set(S context, B component) {
        return setter.apply(context, component);
    }
}
