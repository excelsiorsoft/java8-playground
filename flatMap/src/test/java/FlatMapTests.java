import static java.util.Arrays.asList;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.Test;

public class FlatMapTests {

	@Test
	public void concatStreams(){
		Stream<String> first = asList("A", "B", "C").stream();
		Stream<String> second = asList("D", "E", "F").stream();
		Stream<String> third = asList("G", "H", "I").stream();
		
		Stream<Stream<String>> multilevelStream = Stream.of(first, second, third);
		Stream<String> flattenedStream = multilevelStream.flatMap(Function.identity());
		
		flattenedStream.forEach(System.out::print);
	}
	
}
