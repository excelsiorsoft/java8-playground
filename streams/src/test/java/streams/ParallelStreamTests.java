package streams;



import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;
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
	
	
	
	@Test
	public void raceConditions() throws Exception {
		

		List<String> strings = new ArrayList<>(); //not thread-safe
		
		Stream.iterate("+", s -> s+"+")
		.parallel()
		.limit(10000)
		//.peek(e -> System.out.println(e+" is processed by "+ Thread.currentThread().getName()))
		.forEach(e -> strings.add(e));
		
		System.out.println("# of elems: "+strings.size());
	}
	
	@Test
	public void threadSafe() throws Exception {
		
		List<String> strings = new CopyOnWriteArrayList<>(); //concurrent-aware

		Stream.iterate("+", s -> s+"+")
		.parallel()
		.limit(10000)
		//.peek(e -> System.out.println(e+" is processed by "+ Thread.currentThread().getName()))
		.forEach(e -> strings.add(e));
		
		System.out.println("# of elems: "+strings.size());
	}
	
	@Test
	public void betterWay() throws Exception {

		List<String> strings = 
		Stream.iterate("+", s -> s+"+")
		.parallel()
		.limit(10000)
		//.peek(e -> System.out.println(e+" is processed by "+ Thread.currentThread().getName()))
		.collect(Collectors.toList());
		
		System.out.println("# of elems: "+strings.size());
	}
	

}
