package supplier;

import static java.util.Arrays.asList;

import java.awt.Choice;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.junit.Test;

import supplier.Shape.Circle;
import supplier.Shape.Rectangle;
import supplier.Shape.Square;

public class ObjectFactoryTest {

	ObjectFactory cut = new ObjectFactory();
	
	@Test
	public void construction() {
		
<<<<<<< HEAD
		Person personSupplier = ObjectFactory.bind(Person::new, "Simeon").get();
		System.out.println(personSupplier);

=======
		System.out.println(cut.bind(Person::new, "Simeon").get());
		
>>>>>>> branch 'master' of https://github.com/excelsiorsoft/java8-playground.git
	}
	
	
	


}
