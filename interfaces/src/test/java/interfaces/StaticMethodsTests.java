package interfaces;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class StaticMethodsTests {
	
	@Test
	public void testStatics() {
		List<String> bonds = Arrays.asList("Connery", "Lazenby", "Moore",
			    "Dalton", "Brosnan", "Craig");
		
		List<String> sorted = bonds.stream()
			    .sorted(Comparator.naturalOrder())
			    .collect(Collectors.toList());
		
		System.out.println("sorted: " + sorted);
		
		sorted = bonds.stream()
				.sorted(Comparator.comparingInt(String::length)
				.thenComparing(Comparator.naturalOrder()))
				.collect(Collectors.toList());
		
		System.out.println("sorted: " + sorted);
		
		sorted = bonds.stream()
			    .sorted(Comparator.comparing(String::toLowerCase)) 
			    .collect(Collectors.toList());
		
		System.out.println("sorted: " + sorted);
	}

}
