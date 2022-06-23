package com.dfsek.protolith.kind;

/**
 * A higher-kinded type with one generic type, {@code T<A>}
 *
 * @param <T> The base type
 * @param <A> The first generic
 */
public interface Kind1<T, A> extends Kind<T> {
}
