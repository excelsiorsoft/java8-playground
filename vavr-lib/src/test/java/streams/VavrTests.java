package streams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
//import java.util.List;
import java.util.TreeMap;

import org.junit.Test;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Queue;
import io.vavr.collection.Stream;

import io.vavr.control.Option;

public class VavrTests {

	

	@Test
	public void unmod() {

		java.util.List<String> otherList = Arrays.asList("1", "2", "3", "4", "5");
		java.util.List<String> list = Collections.unmodifiableList(otherList);


		Throwable thrown = catchThrowable(() -> {
			assertThat(
					// Boom!
					list.add("why not?"));
		});
		
		assertThat(thrown).isInstanceOf(UnsupportedOperationException.class).hasNoCause()
				.hasStackTraceContaining("UnsupportedOperationException");

	}
	
	@Test public void queue1() {
		Queue<Integer> queue = Queue.of(1, 2, 3);

		// = (1, Queue(2, 3))
		Tuple2<Integer, Queue<Integer>> dequeued =
		        queue.dequeue();
		
		System.out.println(queue);
		System.out.println(dequeued);
		
		// = Some((1, Queue()))
		Option<Tuple2<Integer, Queue<Integer>>> optionInt = 
				Queue.of(1).dequeueOption();
		
		System.out.println(optionInt);

		// = None
		Option<Tuple2<Object, Queue<Object>>> optionObj = 
				Queue.empty().dequeueOption();
		
		System.out.println(optionObj);
	}
	
	@Test public void queue2() {
		// = Queue(1)
		Queue<Integer> queue = Queue.of(1);

		// = Some((1, Queue()))
		Option<Tuple2<Integer, Queue<Integer>>> dequeued =
		        queue.dequeueOption();

		// = Some(1)
		Option<Integer> element = dequeued.map(Tuple2::_1);
		System.out.println(element);

		// = Some(Queue())
		Option<Queue<Integer>> remaining =
		        dequeued.map(Tuple2::_2);
		
		
	}
	
	@Test public void collections1() {
		Stream<String> result = Stream.of(1, 2, 3).map(Object::toString);
		System.out.println("as java: "+result.asJava());
		String one = result.head();
		System.out.println(result.tail());
		System.out.println(one);
		String two = result.tail().head();
		System.out.println(two);
		System.out.println(result.tail().tail());
		System.out.println(result.tail().tail().tail());
		System.out.println(result);
		
	}
	
	@Test public void folding() {
		String reversed = io.vavr.collection.List.of("a", "b", "c").foldLeft("!", (xs, x) -> x + xs);
		System.out.println(reversed);
		
		String flattened = io.vavr.collection.List.of("a", "b", "c").mkString(", ");
		System.out.println(flattened);
		
		String interspersed = io.vavr.collection.List.of("a", "b", "c")
				.intersperse(", ")
				.foldLeft(new StringBuilder(), StringBuilder::append)
				.toString();
		System.out.println(interspersed);
	}
	
	@Test public void tupleAndMaps() {
		Tuple2<Integer, String> entry = Tuple.of(1, "A");

		Integer key = entry._1;
		String value = entry._2;
		//[key:1 => value: A]
		System.out.println("[key:"+key +" => "+ "value: "+value+"]");
		
		
		Map<Object, List<Integer>> groupped = List.of(1, 2, 3, 4).groupBy(i -> i % 2);
		// LinkedHashMap((1, List(1, 3)), (0, List(2, 4)))
		System.out.println(groupped);
		TreeMap<Object,List<Integer>> sorted = new TreeMap<>();
		groupped.keySet().toStream().forEach(kee -> sorted.put(kee,groupped/*Option*/.get(kee).get()));
		//{0=List(2, 4), 1=List(1, 3)}
		System.out.println(sorted);
		

		// = List((a, 0), (b, 1), (c, 2))
		System.out.println(List.of('a', 'b', 'c').zipWithIndex());
		
		
	}

	@Test
	public void init() {

		java.util.List<Integer> list = Collections.unmodifiableList(
				new ArrayList<Integer>() {
			{
				add(2);
				add(3);
				add(4);
			}
		});
		System.out.println(list);
		
		java.util.Map<Integer, String> map = Collections.unmodifiableMap(new HashMap<Integer, String>() {
			{
				put(1, "one");
				put(2, "two");
				put(3, "three");
			}
		});

		System.out.println(map);
	}

	@Test public void tuples() {
		// (Java, 8)
		Tuple2<String, Integer> java8 = Tuple.of("Java", 8); 
		assertThat(java8).hasToString("(Java, 8)");

		assertThat(java8._1).isEqualTo("Java");
		assertThat(java8._2).isEqualTo(8);

		//component-wise map
		Tuple2<String, Integer> that = java8.map(
		        s -> s.substring(2) + "vr",
		        i -> i / 8
		);
		
		assertThat(that._1).isEqualTo("vavr");
		assertThat(that._2).isEqualTo(1);
		
		Tuple2<String, Integer> that2 = java8.map(
		        (s, i) -> Tuple.of(s.substring(2) + "vr", i / 8)
		);
		
		assertThat(that2._1).isEqualTo("vavr");
		assertThat(that2._2).isEqualTo(1);
	}

}
