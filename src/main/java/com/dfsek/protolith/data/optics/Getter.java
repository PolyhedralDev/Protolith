package com.dfsek.protolith.data.optics;

/**
 * A function which allows getting a component {@link A} from a context {@link  S}.
 * @param <A> Component type.
 * @param <S> Context type.
 */
public interface Getter<A, S> extends NarrowOptic<A, S> {
    A view(S context);
}
