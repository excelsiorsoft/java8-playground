package com.excelsiorsoft.postcondition;

import java.math.BigDecimal;
import java.util.function.Function;

import org.junit.Test;

public class PostconditionTest {

	@Test
	public void testPostcondition() {
        System.out.println("-----------------------------------------");
        System.out.println("Starting POSTCONDITION combinator demo...");
        System.out.println("-----------------------------------------");

        Function<BigDecimal, String> addTaxDecorated1 = Postcondition.decorate(
                this::addTax,
                this::checkResultStartsWith$,
                InvalidTaxResultFormatException::new);

        String result1 = addTaxDecorated1.apply(new BigDecimal("10"));

        System.out.println("Done - Result is " + result1);
        System.out.println("-----------------------------------------");

        try {
            Function<BigDecimal, String> addTaxDecorated2 = Postcondition.decorate(
                    this::addTaxIncorrect,
                    this::checkResultStartsWith$,
                    InvalidTaxResultFormatException::new);

            String result2 = addTaxDecorated2.apply(new BigDecimal("10"));
            System.out.println("Done - Result is " + result2);

        } catch (InvalidTaxResultFormatException e) {

            System.out.println("Exception: " + e.getMessage());
        }
        System.out.println("-----------------------------------------");
    }

    private String addTax(BigDecimal amount) {
        System.out.println("Adding heavy taxes to poor citizen...");
        return "$" + amount.multiply(new BigDecimal("1.22"));
    }

    private String addTaxIncorrect(BigDecimal amount) {
        System.out.println("Incorrectly adding heavy taxes to poor citizen...");
        return "€" + amount.multiply(new BigDecimal("1.22"));
    }

    private boolean checkResultStartsWith$(BigDecimal argument, String result) {
        boolean condition = result.startsWith("$");
        System.out.println("POSTCONDITION: Argument is " + argument + ", result is "
                + result + ", condition is " + condition);
        return condition;
    }

}
