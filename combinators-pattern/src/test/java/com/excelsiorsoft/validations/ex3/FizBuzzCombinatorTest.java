package com.excelsiorsoft.validations.ex3;

import static org.junit.Assert.*;

import org.junit.Test;

public class FizBuzzCombinatorTest {

	@Test
	public void test() {
		String twenty = FizBuzzCombinator.withCombinator().apply(20);
		System.out.println(twenty);
		String fifteen = FizBuzzCombinator.withCombinator().apply(15);
		System.out.println(fifteen);
	}

}
