package streams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import io.vavr.Tuple2;
import io.vavr.collection.Queue;
import io.vavr.collection.Stream;
import io.vavr.control.Option;

public class VavrTests {

	

	@Test
	public void unmod() {

		List<String> otherList = Arrays.asList("1", "2", "3", "4", "5");
		List<String> list = Collections.unmodifiableList(otherList);


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

}
