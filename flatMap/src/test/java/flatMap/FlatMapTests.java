package flatMap;

import static java.lang.ClassLoader.getSystemResource;
import static java.nio.file.Paths.get;
import static java.util.Arrays.asList;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Set;
<<<<<<< HEAD
import java.util.SortedSet;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
	
	@Test
	public void splittingFileIntoWords() throws Exception {
		
		Function<String, Stream<String>> splitIntoWords = line -> Pattern.compile("[\" \",.]").splitAsStream(line);
				
		try (Stream<String> first = Files.lines(get(getSystemResource("flatMap/text1.txt").toURI()));
			 Stream<String> second = Files.lines(get(getSystemResource("flatMap/text2.txt").toURI()));
			 Stream<String> third = Files.lines(get(getSystemResource("flatMap/text3.txt").toURI()));) {
			 
			Set<String> words = Stream.of(first, second, third)
				.flatMap(Function.identity())
				.flatMap(splitIntoWords)
				.map(String::toLowerCase)
				.distinct()
				.collect(Collectors.toSet());
			
			System.out.println(words.size() + ": "+ words);
=======
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
	
	@Test
	public void splittingFileIntoWords() throws Exception {
		
		Function<String, Stream<String>> splitIntoWords = line -> Pattern.compile(" ").splitAsStream(line);
				
		try (Stream<String> first = Files.lines(get(getSystemResource("flatMap/text1.txt").toURI()));
			 Stream<String> second = Files.lines(get(getSystemResource("flatMap/text2.txt").toURI()));
			 Stream<String> third = Files.lines(get(getSystemResource("flatMap/text3.txt").toURI()));) {
			 
			Set<String> words = Stream.of(first, second, third)
				.flatMap(Function.identity())
				.flatMap(splitIntoWords)
				.collect(Collectors.toSet());
			
			System.out.println(words);
>>>>>>> branch 'master' of https://github.com/excelsiorsoft/java8-playground.git
		

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
}
