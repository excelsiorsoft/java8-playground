package supplier;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import supplier.Shape.Circle;
import supplier.Shape.Rectangle;
import supplier.Shape.Square;

public class PersonTest {

	
	
	@Test
	public void mapping() {
		List<String> names =    asList("Grace Hopper", "Barbara Liskov", "Ada Lovelace", "Karen Spärck Jones");
		List<Person> people = names.stream().map(Person::new).collect(Collectors.toList());
		System.out.println("list of String to list of Person: " + people);
		
		List<Shape> shapes = asList(Square::new, Rectangle::new, Circle::new);
		Map<String, Shape> result = shapes.stream().collect(Collectors.toMap(Shape::name, Function.identity()));
		System.out.println("list to map: " +result);

	}
	
	@Test
	public void differentConstructors() {

		Person before = new Person("Grace Hopper");

		// convert to list
		List<Person> people = Stream.of(before).collect(Collectors.toList());
		Person after = people.get(0);

		assertTrue(before == after);

		before.setName("Grace Murray Hopper");
		assertEquals("Grace Murray Hopper", after.getName());

		// convert to list
		people = Stream.of(before)
				/* Function<Person, Person> requires copy constructor */
				.map(Person::new).collect(Collectors.toList());
		after = people.get(0);
		assertFalse(before == after);
		assertEquals(before, after);
		before.setName("Rear Admiral Dr. Grace Murray Hopper");
		assertFalse(before.equals(after));
	}
	
	@Test
	public void varargConstructor() {
		List<Person> names =  asList("Grace Hopper", "Barbara Liskov", "Ada Lovelace", "Karen Spärck Jones").
		
		stream()
			.map(name -> name.split(" "))
			//String[] requires vararg constructor on Person
			.map(Person::new)
			.collect(Collectors.toList());

		System.out.println("Varargs ctor, names=" + Arrays.asList(names));
	}

}
