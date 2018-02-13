package streams;



import java.util.stream.Stream;

import org.junit.Test;

public class ParallelStreamTests {

	@Test
	public void orderedTriangle() throws Exception {
		Stream.iterate("+", s -> s+"+")
		.limit(6)
		.peek(e -> System.out.println(e+" is processed by "+ Thread.currentThread().getName()))
		.forEach(System.out::println);
	}

	@Test
	public void unorderedTriangle() throws Exception {
		Stream.iterate("+", s -> s+"+")
		.parallel() //executed in the common fork-join pull by multiple threads 
		.limit(6)
		.peek(e -> System.out.println(e+" is processed by "+ Thread.currentThread().getName())) //
		.forEach(System.out::println);
	}
	
	@Test
	public void unorderedTriangleLimitedPool() throws Exception {
		
		System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "2");
		Stream.iterate("+", s -> s+"+")
		.parallel() //executed in the common fork-join pull by multiple threads 
		.limit(6)
		.peek(e -> System.out.println(e+" is processed by "+ Thread.currentThread().getName())) //
		.forEach(System.out::println);
	}
	

}