package com.dfsek.protolith.kind;

import java.util.function.Supplier;

/**
 * Java does not support higher-kinded types. These interfaces exist to simulate them.
 * @param <T>
 */
public interface Kind<T> extends Supplier<T> {
}
