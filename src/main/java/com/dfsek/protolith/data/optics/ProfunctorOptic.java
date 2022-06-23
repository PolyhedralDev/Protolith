package com.dfsek.protolith.data.optics;

import com.dfsek.protolith.kind.Kind2;
import io.vavr.Function1;

public interface ProfunctorOptic<K, A, B, S, T> extends Function1<Kind2<K, A, B>, Kind2<K, S, T>> {
    default <C, D> ProfunctorOptic<K, C, D, S, T> compose(ProfunctorOptic<K, C, D, A, B> f) {
        return g -> apply(f.apply(g));
    }
}
