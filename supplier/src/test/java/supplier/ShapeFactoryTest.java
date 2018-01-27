package supplier;

import java.util.function.Supplier;

import org.junit.Test;

public class ShapeFactoryTest {

	@Test
	public void construction() {
		
		//object construction is a supplier
		Supplier<ShapeFactory> shapeFactory = ShapeFactory::new;
		
		// call draw method of circle
		shapeFactory.get().createShape("circle").draw();
		
		// call draw method of Rectangle
		shapeFactory.get().createShape("rectangle").draw();
		
		shapeFactory.get().createShape("square").draw();
	}

}
