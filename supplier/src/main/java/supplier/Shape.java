package supplier;

public interface Shape {
	void draw();

	public static class Rectangle implements Shape {
		@Override
		public void draw() {
			System.out.println("Inside Rectangle::draw() method.");
		}
	}

	public static class Circle implements Shape {
		@Override
		public void draw() {
			System.out.println("Inside Circle::draw() method.");
		}
	}

	public static class Square implements Shape {
		@Override
		public void draw() {
			System.out.println("Inside Square::draw() method.");
		}
	}
}