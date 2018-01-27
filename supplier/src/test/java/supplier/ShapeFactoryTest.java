package supplier;

import static java.util.Arrays.asList;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.junit.Test;

import supplier.Shape.Circle;
import supplier.Shape.Rectangle;
import supplier.Shape.Square;

public class ShapeFactoryTest {

	@Test
	public void construction() {
		
		//object construction IS a supplier
		Supplier<ShapeFactory> shapeFactory = ShapeFactory::new;

		shapeFactory.get().createShape("circle").draw();
		
		shapeFactory.get().createShape("rectangle").draw();
		
		shapeFactory.get().createShape("square").draw();
		
		List<Supplier<Shape>> suppliers = asList(Square::new, Rectangle::new, Circle::new);
		System.out.println("constructor invocations list:\n\t" + suppliers);
	}
	


}
