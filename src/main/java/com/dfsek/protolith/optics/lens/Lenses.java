package com.dfsek.protolith.optics.lens;


import io.vavr.collection.List;
import io.vavr.control.Option;

public final class Lenses {
    public static <T> SimpleLens<List<T>, Option<T>> listIndex(int index) {
        return SimpleLens.lens(
                list -> {
                    if(index >= list.size()) return Option.none();
                    else return Option.of(list.get(index));
                },
                (list, value) -> value.map(t -> {
                    if(index >= list.size()) return list.update(index, t);
                    else return list;
                }).getOrElse(list)
        );
    }
}
