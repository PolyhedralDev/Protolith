package com.dfsek.protolith.kind;

/**
 * A higher-kinded type with three generic types, {@code T<A, B, C>}
 *
 * @param <T> The base type
 * @param <A> The first generic
 */
public interface Kind3<T, A, B, C> extends Kind2<T, A, B> {
}
