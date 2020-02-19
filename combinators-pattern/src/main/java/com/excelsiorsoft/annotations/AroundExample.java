package com.excelsiorsoft.annotations;

/**
 * @author Simeon
 *
 */
import java.math.BigDecimal;
import java.util.function.BiConsumer;
import java.util.function.Function;

@FunctionalInterface
interface Around<T, R>
        extends Function<Function<T, R>,
                Function<BiConsumer<Around.Executable<R>, T>,
                Function<T, R>>> {

    @FunctionalInterface
    interface Executable<R> {
        R execute();
    }

    static <T, R> Around<T, R> create() {
        return function -> around -> argument -> {
            @SuppressWarnings("unchecked")
            R[] result = (R[]) new Object[1];
            Executable<R> callback = () -> result[0] = function.apply(argument);
            around.accept(callback, argument);
            return result[0];
        };
    }

    static <T, R> Function<T, R> decorate(
            Function<T, R> function,
            BiConsumer<Executable<R>, T> around) {
        return Around.<T, R>create().apply(function).apply(around);
    }
}

public class AroundExample {

    void demo() {
        System.out.println("----------------------------------");
        System.out.println("Starting AROUND combinator demo...");
        System.out.println("----------------------------------");

        Function<BigDecimal, String> addTaxDecorated =
                Around.decorate(this::addTax, this::around);

        String result = addTaxDecorated.apply(new BigDecimal("10000"));

        System.out.println("Done - Result is " + result);
        System.out.println();
    }

    private String addTax(BigDecimal amount) {
        System.out.println("Adding heavy taxes to poor citizen...");
        return "$" + amount.multiply(new BigDecimal("1.22"));
    }

    private void around(Around.Executable<String> function, BigDecimal argument) {
        System.out.println("BEFORE: Argument is " + argument);
        String result = function.execute(); // original function executed here!
        System.out.println("AFTER: Result is " + result);
    }
}
