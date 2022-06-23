package com.dfsek.protolith.kind;

/**
 * A higher-kinded type with two generic types, {@code T<A, B>}
 *
 * @param <T> The base type
 * @param <A> The first generic
 */
public interface Kind2<T, A, B> extends Kind1<T, A> {
}
