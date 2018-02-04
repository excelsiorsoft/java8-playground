package streams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static io.vavr.API.*;        // $, Case, Match
import static io.vavr.Predicates.*; // instanceOf

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;
import java.util.TreeMap;
import java.util.function.Supplier;

import org.junit.Test;
import org.junit.Test.None;

import io.vavr.Function0;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Function3;
import io.vavr.Function5;
import io.vavr.Lazy;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Queue;
import io.vavr.collection.Stream;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Option.Some;
import io.vavr.control.Try;
import junit.framework.Assert;

public class VavrTests {

	

	@Test
	public void unmod() {

		java.util.List<String> otherList = Arrays.asList("1", "2", "3", "4", "5");
		java.util.List<String> list = Collections.unmodifiableList(otherList);


		Throwable thrown = catchThrowable(() -> {
			
					// Boom!
					list.add("why not?");
		});
		
		assertThat(thrown).isInstanceOf(UnsupportedOperationException.class).hasNoCause()
				.hasStackTraceContaining("UnsupportedOperationException");

	}
	
	@Test public void queue1() {
		Queue<Integer> queue = Queue.of(1, 2, 3);

		// = (1, Queue(2, 3))
		Tuple2<Integer, Queue<Integer>> dequeued =
		        queue.dequeue();
		
		System.out.println(queue);
		System.out.println(dequeued);
		
		// = Some((1, Queue()))
		Option<Tuple2<Integer, Queue<Integer>>> optionInt = 
				Queue.of(1).dequeueOption();
		
		System.out.println(optionInt);

		// = None
		Option<Tuple2<Object, Queue<Object>>> optionObj = 
				Queue.empty().dequeueOption();
		
		System.out.println(optionObj);
	}
	
	@Test public void queue2() {
		// = Queue(1)
		Queue<Integer> queue = Queue.of(1);

		// = Some((1, Queue()))
		Option<Tuple2<Integer, Queue<Integer>>> dequeued =
		        queue.dequeueOption();

		// = Some(1)
		Option<Integer> element = dequeued.map(Tuple2::_1);
		System.out.println(element);

		// = Some(Queue())
		Option<Queue<Integer>> remaining =
		        dequeued.map(Tuple2::_2);
		
		
	}
	
	@Test public void collections1() {
		Stream<String> result = Stream.of(1, 2, 3).map(Object::toString);
		System.out.println("as java: "+result.asJava());
		String one = result.head();
		System.out.println(result.tail());
		System.out.println(one);
		String two = result.tail().head();
		System.out.println(two);
		System.out.println(result.tail().tail());
		System.out.println(result.tail().tail().tail());
		System.out.println(result);
		
	}
	
	@Test public void folding() {
		String reversed = io.vavr.collection.List.of("a", "b", "c").foldLeft("!", (xs, x) -> x + xs);
		System.out.println(reversed);
		
		String flattened = io.vavr.collection.List.of("a", "b", "c").mkString(", ");
		System.out.println(flattened);
		
		String interspersed = io.vavr.collection.List.of("a", "b", "c")
				.intersperse(", ")
				.foldLeft(new StringBuilder(), StringBuilder::append)
				.toString();
		System.out.println(interspersed);
	}
	
	@Test public void tupleAndMaps() {
		Tuple2<Integer, String> entry = Tuple.of(1, "A");

		Integer key = entry._1;
		String value = entry._2;
		//[key:1 => value: A]
		System.out.println("[key:"+key +" => "+ "value: "+value+"]");
		
		
		Map<Object, List<Integer>> groupped = List.of(1, 2, 3, 4).groupBy(i -> i % 2);
		// LinkedHashMap((1, List(1, 3)), (0, List(2, 4)))
		System.out.println(groupped);
		TreeMap<Object,List<Integer>> sorted = new TreeMap<>();
		groupped.keySet().toStream().forEach(kee -> sorted.put(kee,groupped/*Option*/.get(kee).get()));
		//{0=List(2, 4), 1=List(1, 3)}
		System.out.println(sorted);
		

		// = List((a, 0), (b, 1), (c, 2))
		System.out.println(List.of('a', 'b', 'c').zipWithIndex());
		
		
	}

	@Test
	public void init() {

		java.util.List<Integer> list = Collections.unmodifiableList(
				new ArrayList<Integer>() {
			{
				add(2);
				add(3);
				add(4);
			}
		});
		System.out.println(list);
		
		java.util.Map<Integer, String> map = Collections.unmodifiableMap(new HashMap<Integer, String>() {
			{
				put(1, "one");
				put(2, "two");
				put(3, "three");
			}
		});

		System.out.println(map);
	}

	@Test public void tuples() {
		// (Java, 8)
		Tuple2<String, Integer> java8 = Tuple.of("Java", 8); 
		assertThat(java8).hasToString("(Java, 8)");

		assertThat(java8._1).isEqualTo("Java");
		assertThat(java8._2).isEqualTo(8);

		//component-wise map
		Tuple2<String, Integer> that = java8.map(
		        s -> s.substring(2) + "vr",
		        i -> i / 8
		);
		
		assertThat(that._1).isEqualTo("vavr");
		assertThat(that._2).isEqualTo(1);
		
		Tuple2<String, Integer> that2 = java8.map(
		        (s, i) -> Tuple.of(s.substring(2) + "vr", i / 8)
		);
		
		assertThat(that2._1).isEqualTo("vavr");
		assertThat(that2._2).isEqualTo(1);
		
		String r = java8.apply((s, i) -> s.substring(2) + "vr"+ i*3);
		assertThat(r).hasToString("vavr24");

	}
	
	@Test public void functions() {
		
		Function2<Integer, Integer, Integer> sum = (a, b) -> a + b;
		assertThat(sum.apply(2,3)).isEqualTo(5);
		
		Function2<Integer, Integer, Integer> sum1 = Function2.of(this::methodAccepting2Params);
		assertThat(sum1.apply(2,3)).isEqualTo(5);
		
		Function1<Integer, Integer> plusOne = a -> a + 1;
		Function1<Integer, Integer> multiplyByTwo = a -> a * 2;

		//function composition
		Function1<Integer, Integer> add1AndMultiplyBy2 = plusOne.andThen(multiplyByTwo);

		assertThat(add1AndMultiplyBy2.apply(2)).isEqualTo(6);
		

		Function1<Integer, Integer> add1AndMultiplyByTwo = multiplyByTwo.compose(plusOne);

		assertThat(add1AndMultiplyByTwo.apply(2)).isEqualTo(6);
		
		//function lifting
		Function2<Integer, Integer, Integer> divide = (a, b) -> a / b;
		
		Throwable thrown = catchThrowable(() -> {
			divide.apply(4,0);
					
		});
		
		assertThat(thrown).isInstanceOf(java.lang.ArithmeticException.class).hasMessage("/ by zero");
		
		Function2<Integer, Integer, Option<Integer>> safeDivide = Function2.lift(divide);

		// = None
		Option<Integer> i1 = safeDivide.apply(1, 0); 
		assertThat(i1).isNotInstanceOf(java.lang.ArithmeticException.class);
		assertThat(i1).isInstanceOf(Option.None.class);

		// = Some(2)
		Option<Integer> i2 = safeDivide.apply(4, 2);
		assertThat(i2).isInstanceOf(Option.Some.class);
		
		Throwable thrownIAE = catchThrowable(() -> {
			partialSum(-1,2);
					
		});
		
		assertThat(thrownIAE).isInstanceOf(java.lang.IllegalArgumentException.class);
		
		Function2<Integer, Integer, Option<Integer>> partialSum = Function2.lift(this::partialSum);

		// = None
		Option<Integer> optionalResult = partialSum.apply(-1, 2); 
		assertThat(optionalResult).isNotInstanceOf(java.lang.IllegalArgumentException.class);
		assertThat(optionalResult).isInstanceOf(Option.None.class);

		
	}
	
	@Test public void functions1() {
		
		//memoization
		Function0<Double> hashCache =
		        Function0.of(Math::random).memoized();

		double randomValue1 = hashCache.apply();
		double randomValue2 = hashCache.apply();

		assertThat(randomValue1).isEqualTo(randomValue2);
		
		//patial application
		Function2<Integer, Integer, Integer> sum = (a, b) -> a + b;
		Function1<Integer, Integer> add2 = sum.apply(2); 

		assertThat(add2.apply(4)).isEqualTo(6);
		
		Function5<Integer, Integer, Integer, Integer, Integer, Integer> add = (a, b, c, d, e) -> a + b + c + d + e;
		Function2<Integer, Integer, Integer> add3 = add.apply(2, 3, 1); 

		assertThat(add3.apply(4, 3)).isEqualTo(13);
		
		//currying
		Function2<Integer, Integer, Integer> sumCurr = (a, b) -> a + b;
		Function1<Integer, Integer> sumCurr_ = sum.curried().apply(2); 

		assertThat(sumCurr_.apply(4)).isEqualTo(6);
		
		Function3<Integer, Integer, Integer, Integer> funct = (a, b, c) -> a + b + c;
		final Function1<Integer, Function1<Integer, Integer>> addOne = funct.curried().apply(2);

		assertThat(addOne.apply(4).apply(3)).isEqualTo(9); //N.B.: multiple apply calls
	}
	

	@Test public void options() {
		
		Optional<String> maybeFoo = Optional.of("foo"); 
		assertThat(maybeFoo.get()).isEqualTo("foo");
		Optional<String> maybeFooBar = maybeFoo.map(s -> (String)null)  
		                                       .map(s -> s.toUpperCase() + "bar");
		assertThat(maybeFooBar.isPresent()).isFalse();
		
		//different handling of nulls in Vavr
		Option<String> vVrMaybeFoo = Option.of("foo"); 
		assertThat(vVrMaybeFoo.get()).isEqualTo("foo");
		try {
		    vVrMaybeFoo.map(s -> (String)null) 
		            .map(s -> s.toUpperCase() + "bar"); 
		    //Assert.fail();
		} catch (NullPointerException e) {
			assertThat(e).isInstanceOf(NullPointerException.class);
		}
		
		//preferred way
		Option<String> vVrMaybeFoo1 = Option.of("foo"); 
		assertThat(vVrMaybeFoo1.get()).isEqualTo("foo");
		Option<String> vVrMaybeFooBar = vVrMaybeFoo1.map(s -> (String)null) 
		                                     .flatMap(s -> Option.of(s) 
		                                                         .map(t -> t.toUpperCase() + "bar"));
		assertThat(vVrMaybeFooBar.isEmpty()).isTrue();
		
		//alternative way by co-locating flatMap
		Option<String> maybeFoo_ = Option.of("foo"); 
		assertThat(maybeFoo_.get()).isEqualTo("foo");
		Option<String> maybeFooBar_ = maybeFoo_.flatMap(s -> Option.of((String)null)) 
		                                     .map(s -> s.toUpperCase() + "bar");
		assertThat(maybeFooBar_.isEmpty()).isTrue();
	}
	
	@Test public void trials() {
		Supplier<Integer> other = () -> 10;
		Object result = Try.of(() -> bunchOfWork()).getOrElse(other);
		assertThat(result).isEqualTo(other.get());
		
		
		Object outcome = Try.of(this::bunchOfWork)
			    .recover(x -> Match(x).of(
			        Case($(instanceOf(IllegalStateException.class)), t -> somethingWithException(t)),
			        Case($(instanceOf(IllegalArgumentException.class)), t -> somethingWithException(t)),
			        Case($(instanceOf(UnsupportedOperationException.class)), t -> somethingWithException(t))
			    ))
			    .getOrElse(other);
		assertThat(outcome).isEqualTo(1000);
		
	}
	
	@Test public void lazy() {
		Lazy<Double> lazy = Lazy.of(Math::random);
		lazy.isEvaluated(); 				// = false
		Double result = lazy.get();         // = 0.123 (random generated)
		lazy.isEvaluated(); 				// = true
		Double secondResult = lazy.get();   // = as before (memoized)
		assertThat(result).isEqualTo(secondResult);
		
		CharSequence chars = Lazy.val(() -> "Yay!", CharSequence.class); //N.B.: works ONLY with interfaces, i.e. CharSequence.	
		assertThat(chars).isEqualTo("Yay!");
	}
	
	@Test public void either() {
		
		for(int ji=0; ji<20;ji++) {
			Either<String,Double> value = compute().right().map(i -> i * 2).toEither();
			System.out.println("outcome: "+value+"\n");
		}
	}
	
	private Either<String, Double> compute() {
		double dice = new Random().nextDouble();
		System.out.println("dice: "+dice);
		return (dice > 0.5)? Right(dice):Left("error");
		
	}

	private Object somethingWithException(Exception t) {
		
		return 1000;
	}

	private Object bunchOfWork() {throw new IllegalStateException();}
	

	private int methodAccepting2Params(int a, int b) {
		return a + b;

	}
	

	private int partialSum(int first, int second) {
	    if (first < 0 || second < 0) {
	        throw new IllegalArgumentException("Only positive integers are allowed"); 
	    }
	    return first + second;
	}
	

	

}
