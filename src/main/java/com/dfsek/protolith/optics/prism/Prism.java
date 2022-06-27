package com.dfsek.protolith.optics.prism;

import com.dfsek.protolith.functor.Functor;
import com.dfsek.protolith.functor.Profunctor;
import com.dfsek.protolith.functor.functors.Identity;
import com.dfsek.protolith.functor.profunctors.Cocartesian;
import com.dfsek.protolith.monad.Monad;
import com.dfsek.protolith.optics.Optic;
import com.dfsek.protolith.optics.interact.Matching;
import com.dfsek.protolith.optics.interact.Re;
import com.dfsek.protolith.optics.interact.View;
import io.vavr.Function1;
import io.vavr.Tuple2;
import io.vavr.control.Either;


@FunctionalInterface
public interface Prism<S, T, A, B> extends
        Optic<Cocartesian<?, ?, ?>, Functor<?, ?>, S, T, A, B>,
        Monad<T, Prism<S, ?, A, B>> {
    static <S, T, A, B> Prism<S, T, A, B> adapt(Optic<? super Cocartesian<?, ?, ?>, ? super Functor<?, ?>, S, T, A, B> prism) {
        return new Prism<>() {
            @Override
            public <PN extends Profunctor<?, ?, ? extends Cocartesian<?, ?, ?>>,
                    FN extends Functor<?, ? extends Functor<?, ?>>,
                    FB extends Functor<B, ? extends FN>,
                    FT extends Functor<T, ? extends FN>,
                    PAFB extends Profunctor<A, FB, ? extends PN>,
                    PSFT extends Profunctor<S, FT, ? extends PN>> PSFT apply(PAFB pafb) {
                return prism.apply(pafb);
            }
        };
    }

    static <S, T, A, B> Prism<S, T, A, B> prism(Function1<S, Either<T, A>> match, Function1<B, T> build) {
        return adapt(Optic.<Cocartesian<?, ?, ?>, Functor<?, ?>,
                S, T, A, B,
                Functor<B, ? extends Functor<?, ?>>,
                Functor<T, ? extends Functor<?, ?>>,
                Cocartesian<A, Functor<B, ? extends Functor<?, ?>>, ? extends Cocartesian<?, ?, ?>>,
                Cocartesian<S, Functor<T, ? extends Functor<?, ?>>, ? extends Cocartesian<?, ?, ?>>>optic(
                aFunctorCocartesian -> aFunctorCocartesian.<T>cocartesian()
                        .diMap(match,
                                functors -> functors.fold(Identity::new, bFunctor -> bFunctor.map(build)))
        ));
    }

    default Function1<? super B, ? extends T> build() {
        return b -> View.view(Re.re(this), b);
    }

    default Function1<? super S, ? extends Either<T, A>> match() {
        return s -> Matching.matching(this).apply(s);
    }

    default Tuple2<Function1<? super B, ? extends T>, Function1<? super S, ? extends Either<T, A>>> deconstruct() {
        return new Tuple2<>(build(), match());
    }

    @Override
    default <U> Prism<S, U, A, B> flatMap(Function1<? super T, ? extends Monad<U, Prism<S, ?, A, B>>> f) {
        return deconstruct()
                .apply((bt, seta) -> Prism.<S, U, A, B>prism(
                        s -> seta.apply(s).<Either<U, A>>fold(t -> Matching.matching(f.apply(t).<Prism<S, U, A, B>>coerce(), s),
                                Either::right),
                        b -> View.<B, B, U, U>view(Re.re(f.apply(bt.apply(b)).coerce())).apply(b)));
    }

    default <U> Prism<S, U, A, B> pure(U u) {
        return prism(Function1.constant(Either.left(u)), Function1.constant(u));
    }
}
