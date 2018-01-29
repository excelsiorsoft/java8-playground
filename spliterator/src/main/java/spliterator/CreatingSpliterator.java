package spliterator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CreatingSpliterator {

	public static void main(String[] args) throws Exception {

		Path path = Paths.get(ClassLoader.getSystemResource("people.txt").toURI());

		try (Stream<String> lines = Files.lines(path);) {

			Stream<Person> people = StreamSupport.stream(new PersonSpliterator(lines.spliterator()), false);
			people.forEach(System.out::println);

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
