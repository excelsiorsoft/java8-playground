package com.excelsiorsoft.validations.ex3;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FizBuzzCombinatorTest {

	@Test
	public void test() {
		assertEquals("Expect Fizz", "Fizz", FizBuzzCombinator.withCombinator().apply(6));

		assertEquals("Expect Buzz", "Buzz", FizBuzzCombinator.withCombinator().apply(20));

		assertEquals("Expect FizzBuzz", "FizzBuzz", FizBuzzCombinator.withCombinator().apply(15));

	}

}
