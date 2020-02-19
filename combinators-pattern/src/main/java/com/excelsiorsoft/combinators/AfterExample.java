package com.excelsiorsoft.combinators;

/**
 * @author Simeon
 *
 */
import java.math.BigDecimal;
import java.util.function.Function;

import java.util.function.BiConsumer;
import java.util.function.Function;

@FunctionalInterface
interface After<T, R>
        extends Function<Function<T, R>,
                Function<BiConsumer<T, R>,
                Function<T, R>>> {

    static <T, R> After<T, R> create() {
        return function -> after -> argument -> {
            R result = function.apply(argument);
            after.accept(argument, result);
            return result;
        };
    }

    static <T, R> Function<T, R> decorate(
            Function<T, R> function,
            BiConsumer<T, R> after) {
        return After.<T, R>create().apply(function).apply(after);
    }
}

public class AfterExample {

    void demo() {
        System.out.println("---------------------------------");
        System.out.println("Starting AFTER combinator demo...");
        System.out.println("---------------------------------");

        Function<BigDecimal, String> addTaxDecorated =
                After.decorate(this::addTax, this::after);

        String result = addTaxDecorated.apply(new BigDecimal("1000"));

        System.out.println("Done - Result is " + result);
        System.out.println();
    }

    private String addTax(BigDecimal amount) {
        System.out.println("Adding heavy taxes to poor citizen...");
        return "$" + amount.multiply(new BigDecimal("1.22"));
    }

    private void after(BigDecimal argument, String result) {
        System.out.println("AFTER: Argument was " + argument + ", Result is " + result);
    }
}
