package optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
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

		double avgAge = average.orElse(42);
		assertThat(avgAge).isEqualTo(42);

		avgAge = average.orElseGet(() -> 42); // supplier will only build when necessary
		assertThat(avgAge).isEqualTo(42);
	}

	@Test
	public void buildingOptionals() {

		Optional<String> empty = Optional.empty();

		Throwable noSuchElem = catchThrowable(() -> {
			Optional.ofNullable(null).get();
		});
		assertThat(noSuchElem).isInstanceOf(NoSuchElementException.class);
		assertThat(Optional.ofNullable(null)).isInstanceOf(Optional.class); // instance of nullable Optional is an empty
																			// Optional
		assertThat(Optional.ofNullable(null)).isEqualTo(empty);

		Throwable thrown = catchThrowable(() -> {
			Optional.of(null);
		});
		assertThat(thrown).isInstanceOf(NullPointerException.class);

		Optional<String> nonEmpty = Optional.of("42");
	}

	public static final class NewMath {

		public static Optional<Double> sqrt(Double d) {
			return d > 0d ? Optional.of(Math.sqrt(d)) : Optional.empty();
		}

		public static Optional<Double> inv(Double d) {
			return d != 0d ? Optional.of(1 / d) : Optional.empty();
		}

	}

}
