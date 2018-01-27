package supplier;

public class Person {
	


	private String name;

	Person(String name){
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Person [name=" + name + "]";
	}

}
