package supplier;

import java.util.function.Supplier;

import org.junit.Test;

public class ShapeFactoryTest {

	@Test
	public void construction() {
		
		//object construction is a supplier
		Supplier<ShapeFactory> shapeFactory = ShapeFactory::new;
		
		// call draw method of circle
		shapeFactory.get().getShape("circle").draw();
		
		// call draw method of Rectangle
		shapeFactory.get().getShape("rectangle").draw();
	}

}
