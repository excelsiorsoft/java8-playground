package com.excelsiorsoft.java_closures_lambda_book;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.junit.Test;


public class Chapter2Tests {


	
	@Test
	public void lambdasWithNoReturnValue() {
		
		Consumer<String> doGreet = name -> System.out.println("Hello, " + name);
	    
		for (String name : Arrays.asList("Alice", "Bob", "Cathy")) {
	      doGreet.accept(name);
	    }
		
		System.out.println("===");
		
		BiConsumer<String,String> printConcat = (left,right) -> System.out.println(left + right);
	    
		for (String name : Arrays.asList("Alice", "Bob", "Cathy")) {
	      printConcat.accept("Goodbye, ", name);
	    }
	}
	

}