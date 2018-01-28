package supplier;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Person {

	private String name;

	Person(String name) {
		this.name = name;
	}

	// copy constructor
	public Person(Person p) {
		this.name = p.name;
	}
	
	//vararg constructor
	public Person(String... names) {
	    this.name = Arrays.stream(names)
	                      .collect(Collectors.joining(" "));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + "]";
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getName() {
		return this.name;
	}

}
