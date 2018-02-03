package streams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

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

}
