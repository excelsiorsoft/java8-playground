package com.excelsiorsoft.annotations;

import static org.junit.Assert.*;

import org.junit.Test;

import com.excelsiorsoft.annotations.AfterExample;

public class AfterExampleTest {

private AfterExample after;
	
	@Test
	public void testAfter() {
		after = new AfterExample();
		after.demo();
	}

}
