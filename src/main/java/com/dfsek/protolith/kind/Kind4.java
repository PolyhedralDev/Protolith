package com.dfsek.protolith.kind;

/**
 * A higher-kinded type with four generic types, {@code T<A, B, C>}
 * @param <T> The base type
 * @param <A> The first generic
 */
public interface Kind4<T, A, B, C, D> extends Kind3<T, A, B, C> {
}
