package optional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import spliterator.Person;

public class OptionalTests {
	
@Test
public void optionals() {
	List<Person> people = new ArrayList<>();
	
	OptionalDouble average = people.stream().mapToInt(p -> p.getAge()).average();
	assertThat(average).isInstanceOf(OptionalDouble.class);
}

}
