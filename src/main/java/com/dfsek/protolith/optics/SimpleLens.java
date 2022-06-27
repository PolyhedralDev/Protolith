package com.dfsek.protolith.optics;

import com.dfsek.protolith.functor.Profunctor;
import com.dfsek.protolith.functor.profunctors.Cartesian;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Tuple2;

public class SimpleLens<S, A> implements Optic<Cartesian<?, ?, ?>, S, S, A, A> {
    private final Lens<S, S, A, A> delegate;

    protected SimpleLens(Lens<S, S, A, A> delegate) {
        this.delegate = delegate;
    }

    @Override
    public <PN extends Profunctor<?, ?, ? extends Cartesian<?, ?, ?>>,
            PAB extends Profunctor<A, A, ? extends PN>,
            PST extends Profunctor<S, S, ? extends PN>> PST apply(PAB pab) {
        return delegate.apply(pab);
    }

    public static <S, A> SimpleLens<S, A> of(Lens<S, S, A, A> lens) {
        return new SimpleLens<>(lens);
    }

    public static <S, A> SimpleLens<S, A> of(Function1<S, A> getter, Function2<S, A, S> setter) {
        return of(new Lens<>(getter, setter));
    }

    public Function1<? super S, ? extends A> getter() {
        return delegate.getter();
    }

    public Function2<? super S, ? super A, ? extends S> setter() {
        return delegate.setter();
    }

    public A get(S context) {
        return delegate.getter().apply(context);
    }

    public S set(S context, A component) {
        return delegate.setter().apply(context, component);
    }
}
