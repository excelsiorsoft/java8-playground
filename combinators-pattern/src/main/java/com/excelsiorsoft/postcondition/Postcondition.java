package com.excelsiorsoft.postcondition;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;


@FunctionalInterface
public interface Postcondition<T, R, X extends RuntimeException>
        extends Function<Function<T, R>,
                Function<BiPredicate<T, R>,
                Function<BiFunction<T, R, X>,
                Function<T, R>>>> {

    static <T, R, X extends RuntimeException> Postcondition<T, R, X> create() {
        return function -> condition -> error -> After.decorate(
                function,
                (argument, result) -> {
                    if (!condition.test(argument, result)) {
                        throw error.apply(argument, result);
                    }
                });
    }

    static <T, R, X extends RuntimeException> Function<T, R> decorate(
            Function<T, R> function,
            BiPredicate<T, R> condition,
            BiFunction<T, R, X> error) {
        return Postcondition.<T, R, X>create().apply(function).apply(condition).apply(error);
    }
}
