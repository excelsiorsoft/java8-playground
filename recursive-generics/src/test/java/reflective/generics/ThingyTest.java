package reflective.generics;

import static org.junit.Assert.*;

import org.junit.Test;

public class ThingyTest {

	@Test
	public void test() {
		Bike b = new Bike();
		Bike bb = new Bike();
		
		Nail n = new Nail();
		Nail nn = new Nail();
		
		b.compareTo(bb);
		//b.compareTo(n); cannot do - does not satisfy the contract
	}

}
