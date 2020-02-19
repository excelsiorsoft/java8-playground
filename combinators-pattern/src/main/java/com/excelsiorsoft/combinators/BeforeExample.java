package com.excelsiorsoft.combinators;


/**
 * 
 */

/**
 * @author Simeon
 *
 */
import java.math.BigDecimal;
import java.util.function.Consumer;
import java.util.function.Function;

@FunctionalInterface
interface Before<T, R> extends Function<Consumer<T>, Function<Function<T, R>, Function<T, R>>> {

	static <T, R> Before<T, R> create() {
		return before -> function -> argument -> {
			before.accept(argument);
			return function.apply(argument);
		};
	}

	static <T, R> Function<T, R> decorate(Consumer<T> before, Function<T, R> function) {
		return Before.<T, R>create().apply(before).apply(function);
	}
}

public class BeforeExample {

    void demo() {
        System.out.println("----------------------------------");
        System.out.println("Starting BEFORE combinator demo...");
        System.out.println("----------------------------------");

        Function<BigDecimal, String> addTax = this::addTax;

        Consumer<BigDecimal> before = this::before;

        Function<BigDecimal, String> addTaxDecorated =
                Before.decorate(before, addTax);

        BigDecimal argument = new BigDecimal("100");
        String result = addTaxDecorated.apply(argument);

        System.out.println("Done - Result is " + result);
        System.out.println();
    }

    private void before(BigDecimal argument) {
        System.out.println("BEFORE: Argument is " + argument);
    }

    private String addTax(BigDecimal amount) {
        System.out.println("Adding heavy taxes to our poor citizen...");
        return "$" + amount.multiply(new BigDecimal("1.22"));
    }
}
