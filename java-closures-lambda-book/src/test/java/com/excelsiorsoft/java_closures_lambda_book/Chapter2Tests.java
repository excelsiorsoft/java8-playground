package com.excelsiorsoft.java_closures_lambda_book;

import java.util.Arrays;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.DoubleToIntFunction;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongToIntFunction;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import java.util.function.UnaryOperator;

import org.junit.Assert;
import org.junit.Test;


public class Chapter2Tests {

	public /*static*/ void greetFolks(Function<String, String> greeter) {
		for (String name: Arrays.asList("Alice", "Bob", "Cathy")) {
			System.out.println(greeter.apply(name));
		}
	}
	
	public /*static*/ <T,U,V> Function<T,Function<U,V>> curry(BiFunction<T,U,V> bif){
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
	
	
	
	@Test
	public void implicitParticalFunction() {
		BiFunction<String, String, String> concat = (a,b) -> a + b;
		greetFolks(whom -> concat.apply("Hello, ", whom));
	}
	
	
	public <T,U,V> Function<U,V> applyPartial(BiFunction<T,U,V> bif, T firstArgument){
		return u -> bif.apply(firstArgument, u);
	}
	
	@Test
	public void explicitParticalFunction() {
		BiFunction<String, String, String> concat = (a,b) -> a + b;
		greetFolks(applyPartial(concat, "Hello, "));
	}
	
	public String transform(String str, Function<String, String> transformer) {
		return transformer.apply(str);
	}
	
	public CharSequence transform(CharSequence str, Function<CharSequence, CharSequence> transformer) {
		return transformer.apply(str);
	}
	
	@Test
	public void lambdasWithExplicitTyping() {
		Assert.assertEquals("HELLO", transform("Hello", (String str) -> str.toUpperCase()));
		Assert.assertEquals("HELLO", transform("Hello", (CharSequence str) -> ((String)str).toUpperCase()));
		//System.out.println(transform("Hello", (String str) -> str.toUpperCase()));
		
		
	}
	
	//Operator accepts and returns the same type
	UnaryOperator<String> upperCase = str -> str.toUpperCase();
	BinaryOperator<String> concat = (left, right) -> left + right;
	
	//Predicate
	Predicate<String> notNullOrEmpty = s -> s != null && s.length() > 0;
	
	//Functions with primitives
	IntFunction<String> intToString  = i -> Integer.toString(i);
	ToIntFunction<String> parseInt = str -> Integer.valueOf(str);
	IntPredicate isEven = i -> i%2==0;
	ToIntBiFunction<String, String> maxLength = (left, right) -> Math.max(left.length(), right.length());
	IntConsumer printInt = i -> System.out.println(Integer.toString(i));
	ObjIntConsumer<String> printParsedIntWithRadix = (str, radix) -> System.out.println(Integer.parseInt(str, radix));
	IntSupplier randomInt = () -> new Random().nextInt();
	IntUnaryOperator negateInt = i -> -1 * i;
	IntBinaryOperator multiplyInt = (x,y) -> x*y;
	IntToDoubleFunction intAsDouble = i -> Integer.valueOf(i).doubleValue();
	DoubleToIntFunction doubleAsInt = d -> Double.valueOf(d).intValue();
	IntToLongFunction intAsLong = i -> Integer.valueOf(i).longValue();
	LongToIntFunction longAsInt = x -> Long.valueOf(x).intValue();
	

}