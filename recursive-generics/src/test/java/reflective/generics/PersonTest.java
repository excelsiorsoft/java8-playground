package reflective.generics;

import static org.junit.Assert.*;

import org.junit.Test;
import reflective.generics.Person.PersonBuilder;
import reflective.generics.Person.Student;
import reflective.generics.Person.Student.StudentBuilder;

public class PersonTest {

	@Test
	public void testPersonCreation() {
		
		Person p=new PersonBuilder("Bruce","Wayne")
				.withId(10200)
				.withCity("Gotham")
				.build();
		
        System.out.println(p.getFirstName()+" "+p.getLastName()+" "+p.getCity()+" "+p.getAadharId());
	}
	
	@Test 
	public void testStudentCreation() {

		Student s = new StudentBuilder("Peter", "Parker", "undergrad")
				.withId(213).withHobby("very interesting hobby")
				.withCity("Queens")
				.build();
		
		System.out.println(s.getFirstName() + " " + s.getLastName() + " " + s.getCity() + " " + s.getAadharId() + " "
				+ s.getDegree() + " " + s.getHobby());
	}

}
