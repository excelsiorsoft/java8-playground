package streams;



import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

public class TestingAssertions {


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
	 public void testRaceConditions() {
		List<String> strings = new ArrayList<>(); //not thread-safe
		
		int requestedSize = 10_000;
		
		Throwable thrown = catchThrowable(() -> { 
		Stream.iterate("+", s -> s+"+")
		.parallel()
		.limit(requestedSize)
		//.peek(e -> System.out.println(e+" is processed by "+ Thread.currentThread().getName()))
		.forEach(e -> strings.add(e));
		});
		
		SoftAssertions.assertSoftly(softly -> {
		     softly.assertThat(strings.size()).isNotEqualTo(requestedSize);
		     softly.assertThat(thrown).isInstanceOf(ArrayIndexOutOfBoundsException.class);
		 });

	 }
}
