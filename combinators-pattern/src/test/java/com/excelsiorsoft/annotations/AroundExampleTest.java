package com.excelsiorsoft.annotations;

import static org.junit.Assert.*;

import org.junit.Test;

import com.excelsiorsoft.annotations.AroundExample;

public class AroundExampleTest {

private AroundExample around;
	
	@Test
	public void testAround() {
		around = new AroundExample();
		around.demo();
	}

}
