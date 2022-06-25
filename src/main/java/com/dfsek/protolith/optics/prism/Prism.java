package com.dfsek.protolith.optics.prism;

import com.dfsek.protolith.functor.Functor;
import com.dfsek.protolith.functor.Profunctor;
import com.dfsek.protolith.functor.functors.Identity;
import com.dfsek.protolith.functor.profunctors.Cocartesian;
import com.dfsek.protolith.monad.Monad;
import com.dfsek.protolith.optics.Optic;
import com.dfsek.protolith.optics.interact.Re;
import com.dfsek.protolith.optics.interact.Set;
import com.dfsek.protolith.optics.interact.View;
import com.dfsek.protolith.optics.lens.Lens;
import io.vavr.Function1;
import io.vavr.Tuple2;
import io.vavr.control.Either;

import static com.dfsek.protolith.optics.interact.View.view;

@FunctionalInterface
public interface Prism<S, T, A, B> extends
        Optic<Cocartesian<?, ?, ?>, Functor<?, ?>, S, T, A, B>,
        Monad<T, Prism<S, ?, A, B>> {
    static <S, T, A, B> Prism<S, T, A, B> prism(Optic<? super Cocartesian<?, ?, ?>, ? super Functor<?, ?>, S, T, A, B> optic) {
        return new Prism<>() {
            @Override
            public <PN extends Profunctor<?, ?, ? extends Cocartesian<?, ?, ?>>, FN extends Functor<?, ? extends Functor<?, ?>>, FB extends Functor<B, ? extends FN>, FT extends Functor<T, ? extends FN>, PAFB extends Profunctor<A, FB, ? extends PN>, PSFT extends Profunctor<S, FT, ? extends PN>> PSFT apply(PAFB pafb) {
                return optic.apply(pafb);
            }
        };
    }

    static <S, T, A, B> Prism<S, T, A, B> prism(Function1<S, Either<T, A>> match, Function1<B, T> build) {
        return prism(Optic.<Cocartesian<?, ?, ?>, Functor<?, ?>,
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

    default Tuple2<Function1<? super B, ? extends T>, Function1<? super S, ? extends Either<T, A>>> unPrism() {
        return new Tuple2<>(Re.<S, T, A, B>re().andThen(view()), matching(), this);
    }

    @Override
    default <U> Prism<S, U, A, B> flatMap(Function1<? super T, ? extends Monad<U, Prism<S, ?, A, B>>> f) {
        return unPrism()
                .into((bt, seta) -> Prism.<S, U, A, B>prism(
                        s -> seta.apply(s).<Either<U, A>>match(t -> matching(f.apply(t).<Prism<S, U, A, B>>coerce(), s),
                                Either::right),
                        b -> View.<B, B, U, U>view(re(f.apply(bt.apply(b)).coerce())).apply(b)));
    }

    default <U> Prism<S, U, A, B> pure(U u) {
        return prism(Function1.constant(Either.right(u)), Function1.constant(u));
    }
}
