package com.excelsiorsoft.java_closures_lambda_book;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.Test;


public class Chapter2Tests {

	public static void greetFolks(Function<String, String> greeter) {
		for (String name: Arrays.asList("Alice", "Bob", "Cathy")) {
			System.out.println(greeter.apply(name));
		}
	}
	
	public static <T,U,V> Function<T,Function<U,V>> curry(BiFunction<T,U,V> bif){
		return t -> (u -> bif.apply(t,u));
	}
	
	@Test
	public void currying() {
		BiFunction<String, String, String> concat = (a,b) -> a + b;
		Function<String, Function<String, String>> curriedConcat = curry(concat);
		for(String greetings: Arrays.asList("Hello", "Guten Tag", "Bonjour")) {
			greetFolks(curriedConcat.apply(greetings + ", "));
		}
	}
	
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