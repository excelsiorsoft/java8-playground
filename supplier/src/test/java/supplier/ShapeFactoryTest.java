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
		
		List<Shape> shapes = asList(Square::new, Rectangle::new, Circle::new);
		System.out.println("list of shapes: " +shapes);
		

		
		Shape square = Square::new;
		System.out.println("square: "+square);
		square.draw();
	
		Supplier<Shape> suppSquare = Square::new;
		System.out.println("suppSquare: "+suppSquare);

		Number pi = new Double(3.14);
		Double dPi = new Double(3.14);
		
		pi = dPi;

	}
	
	@Test
	public void mapping() {
		List<String> names =    asList("Grace Hopper", "Barbara Liskov", "Ada Lovelace", "Karen Spärck Jones");
		List<Person> people = names.stream().map(Person::new).collect(Collectors.toList());
		System.out.println("list of String to list of Person: " + people);
		
		List<Shape> shapes = asList(Square::new, Rectangle::new, Circle::new);
		Map<String, Shape> result = shapes.stream().collect(Collectors.toMap(Shape::name, Function.identity()));
		System.out.println("list to map: " +result);

	}
	


}
