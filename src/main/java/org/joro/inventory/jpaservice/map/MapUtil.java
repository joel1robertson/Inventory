package org.joro.inventory.jpaservice.map;

import org.joro.inventory.ui.util.LibraryClass;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class MapUtil extends LibraryClass {

    private MapUtil() {
        // library class - do not instantiate
    }

    /**
     * Map a List of "T" objects to a List of "R" objects.
     *
     * @param list the list of "T" objects to map
     * @param mapper the function to map a "T" object to a "R" object
     * @return the mapped list of "R" objects
     * @param <T> the type of the objects to map
     * @param <R> the type of the mapped objects
     */
    public static <T,R> List<R> map(List<T> list, Function<T,R> mapper) {
        return Optional.ofNullable(list).orElse(List.of()).stream()
                .map(mapper)
                .toList();
    }

    /**
     * Map a List of "T" objects to a List of "R" objects.
     *
     * @param list the list of "T" objects to map
     * @param mapper the function to map a "T" object to a "R" object
     * @return the mapped list of "R" objects
     * @param <T> the type of the objects to map
     * @param <R> the type of the mapped objects
     */
    public static <T,K,R> List<R> map(List<T> list, K foreignKeyObject, BiFunction<T,K,R> mapper) {
        return Optional.ofNullable(list).orElse(List.of()).stream()
                .map(t -> mapper.apply(t, foreignKeyObject))
                .toList();
    }
}
