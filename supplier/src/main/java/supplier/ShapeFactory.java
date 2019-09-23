package supplier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import supplier.Shape.Circle;
import supplier.Shape.Rectangle;
import supplier.Shape.Square;

public class ShapeFactory {
	
	final static Map<String, Supplier<Shape>> shapesByCriteria = new HashMap<>();
	
	static {
		shapesByCriteria.put(Circle.class.getSimpleName().toUpperCase(), Circle::new);
		shapesByCriteria.put(Rectangle.class.getSimpleName().toUpperCase(), Rectangle::new);
		shapesByCriteria.put(Square.class.getSimpleName().toUpperCase(), Square::new);
	}

	public Shape createShape(String criteria) {
		Supplier<Shape> shape = shapesByCriteria.get(criteria.toUpperCase());
		if (shape != null) {
			return shape.get();
		}
		throw new IllegalArgumentException("No such shape " + criteria.toUpperCase());
	}
}
