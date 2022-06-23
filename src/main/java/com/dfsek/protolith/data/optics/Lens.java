package com.dfsek.protolith.data.optics;

import io.vavr.Function1;
import io.vavr.Function2;

/**
 * A lens which extracts a component {@link A} from its context {@link S}.
 *
 * @param <A> Component
 * @param <B> Updated component
 * @param <S> Context
 * @param <T> Updated context
 *
 * @param view The view function (expressed in {@link #view()}).
 * @param update The update function (expressed in {@link #update()}).
 */
public record Lens<A, B, S, T>(
        Function1<S, A> view,
        Function2<B, S, T> update
) {
    /**
     * View the component {@link A} on a context instance {@param context}.
     * @param context Context instance.
     * @return Value of component on context instance.
     */
    public A view(S context) {
        return view.apply(context);
    }

    /**
     * Update the component {@link A}, optionally to a new type {@link B} on a context instance {@link S}.
     * @param component New component type/value.
     * @param context Current context.
     * @return New context with supplied type and value.
     */
    public T update(B component, S context) {
        return update.apply(component, context);
    }
}
