package supplier;

public interface Shape {
	void draw();

	default String name() {
		return getClass().getSimpleName();
	}

	public static class Rectangle implements Shape {
		@Override
		public void draw() {
			System.out.println("Inside Rectangle::draw() method.");
		}

/*		@Override
		public String name() {
			return Rectangle.class.getSimpleName();
		}*/
	}

	public static class Circle implements Shape {
		@Override
		public void draw() {
			System.out.println("Inside Circle::draw() method.");
		}

/*		@Override
		public String name() {
			return Circle.class.getSimpleName();
		}*/
	}

	public static class Square implements Shape {
		@Override
		public void draw() {
			System.out.println("Inside Square::draw() method.");
		}

/*		@Override
		public String name() {
			return Square.class.getSimpleName();
		}*/
	}
}