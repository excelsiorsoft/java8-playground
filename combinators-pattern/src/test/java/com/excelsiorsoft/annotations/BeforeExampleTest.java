package com.excelsiorsoft.annotations;

import static org.junit.Assert.*;

import org.junit.Test;

import com.excelsiorsoft.annotations.BeforeExample;

public class BeforeExampleTest {

	private BeforeExample before;
	
	@Test
	public void testBefore() {
		before = new BeforeExample();
		before.demo();
	}

}
