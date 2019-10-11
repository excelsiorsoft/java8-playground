package supplier;

import java.util.function.Function;

import org.junit.Test;

public class SupplierTests {
	
		
	static class OneArg {

	    private final String some;

	    @SuppressWarnings("unchecked")
	    public /*<E extends Exception>*/ OneArg(String some) /*throws E*/ {
	       /* try {*/
	            this.some = some;
	        /*} catch (Exception e) {
	            throw (E) e;
	        }*/
	    }

	    public String getSome() {
	        return some;
	    }
	}
	
	@Test
	public void construction() {
		
		Function<String, OneArg> mcveFactory = OneArg::new;
		
	}

	

}
