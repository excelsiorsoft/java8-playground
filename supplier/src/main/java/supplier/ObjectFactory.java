package supplier;

import java.util.function.Function;
import java.util.function.Supplier;

public class ObjectFactory {

	public static <T, R> Supplier<R> bind(Function<T,R> fn, T val) {
	    return () -> fn.apply(val);
	}
	
}
