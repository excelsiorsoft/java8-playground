package vavr;

// $, Case, Match
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Left;
import static io.vavr.API.Match;
import static io.vavr.API.Right;
import static io.vavr.API.run;
import static io.vavr.Patterns.$Failure;
import static io.vavr.Patterns.$Success;
// instanceOf
import static io.vavr.Predicates.instanceOf;
import static io.vavr.Predicates.isIn;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.TreeMap;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import org.apache.log4j.BasicConfigurator;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vavr.Function0;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Function3;
import io.vavr.Function5;
import io.vavr.Lazy;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import io.vavr.collection.CharSeq;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Queue;
import io.vavr.collection.Seq;
import io.vavr.collection.Stream;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Option.None;
import io.vavr.control.Try.Success;
import io.vavr.control.Try;
import io.vavr.control.Validation;
import io.vavr.test.Arbitrary;
import io.vavr.test.Property;

public class VavrTests {

	final Logger logger = LoggerFactory.getLogger(VavrTests.class);
	{BasicConfigurator.configure();} //https://stackoverflow.com/questions/12532339/no-appenders-could-be-found-for-loggerlog4j

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

		//
		Tuple3<String, Integer, Double> _java8 = Tuple.of("Java", 8, 1.8);
	    String element1 = _java8._1;
	    int element2 = _java8._2();
	    double element3 = _java8._3();
	         
	    assertThat(element1).isEqualTo("Java");
	    assertThat(element2).isEqualTo(8);
	    assertThat(element3).isEqualTo(1.8);

	}
	
	@Test public void functions() {
		
		//Function0 is equivalent to Java 8's Consumer
		Function0<String> getClazzName = () -> this.getClass().getSimpleName();
	    String clazzName = getClazzName.apply();
	    assertThat(clazzName).isEqualTo("VavrTests");

	    
	    //Creating functions
	    Function0.of(getClazzName);
	    Function2.of(this::partialSum);

		
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
		
		//
		Option<Object> noneOption = Option.of(null);
	    Option<Object> someOption = Option.of("val");
	 
	    assertThat(noneOption.toString()).isEqualTo("None");
	    assertThat(someOption.toString()).isEqualTo("Some(val)");
	    
	    //
	    String name = null;
	    Option<String> nameOption = Option.of(name);
	    
	    assertThat(nameOption.getOrElse("default")).isEqualTo("default");
	    

	    name = "valueObtained";
	    nameOption = Option.of(name); //has to re-assign, Options are immutable so the previous assignment won't work.
	 
	    assertThat(nameOption.getOrElse("defaultValue")).isEqualTo("valueObtained");


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
		assertThat(outcome).isEqualTo("IllegalStateException");
		
		//
		Try<Integer> divByZero = Try.of(() -> 1 / 0);
	    assertThat(divByZero.isFailure()).isTrue();

	    int ifError = -1;
		int _outcome = divByZero.getOrElse(ifError);
	    assertThat(_outcome).isEqualTo(ifError);
	    
	    
	    Throwable thrown = catchThrowable(() -> {
	    	divByZero.getOrElseThrow(e->new RuntimeException(e));//re-throw different ex type
					
		});
		
		assertThat(thrown).isInstanceOf(java.lang.RuntimeException.class).hasMessageContaining("/ by zero");
		
		
	}
	
	@Test public void trialWithPatternMatching() {
		
		
		Try<Beverage> vodkaToUnderaged = Try.of(() -> new Transaction().buyBeverage(new Customer(16)));
		assertThat(vodkaToUnderaged).isInstanceOf(Try.Failure.class);
		Try<Beverage> wineToAdult = Try.of(() -> new Transaction().buyBeverage(new Customer(30)));
		assertThat(wineToAdult).isInstanceOf(Try.Success.class);
		System.out.println("alcohol content: "+(wineToAdult.isSuccess()?wineToAdult.get().getAlcoholConcentration():0));
		
		String result = Match(vodkaToUnderaged).of(
		        Case($Success($()), c -> "drinked " + c),
		        Case($Failure($()), t -> "troubles"));
		
		System.out.println(result);
		
		
		Try<Integer> concentration = wineToAdult.map(Beverage::getAlcoholConcentration);
		System.out.println("alcohol content: "+concentration);
		
		result = Match(concentration).of(
		        Case($Success($()), c -> "drinked " + c),
		        Case($Failure($()), t -> "troubles"));
		
		System.out.println(result);
		
		List<Customer> customers = List.of(new Customer(12),new Customer(16), new Customer(30), new Customer(52));
		customers.forEach(
				c -> {
						Try.of(() -> new Transaction().buyBeverage(c))
							.map(Beverage::getAlcoholConcentration)
							.map(Integer::new)
							.forEach(System.out::print);;
	
					});

	}
	
	public final static class Customer {
	    private final int age;
	    public Customer(int age) {
			this.age = age;
		}
		public int getAge() {
	        return age;
	    }
	}
	
	public final static class Beverage {
	    private final  int alcoholConcentration;
	    public int getAlcoholConcentration() {
	        return alcoholConcentration;
	    }
	    
	    public Beverage(int alcohol) {
	    	this.alcoholConcentration = alcohol;
	    }
	}
	
	public final static class UnderageException extends Exception {

		private static final long serialVersionUID = 1L;

		public UnderageException(String message) {
	        super(message);
	    }
	}
	
	public final static class Transaction {
		public Beverage buyBeverage(Customer customer) throws UnderageException {
		    if (customer.getAge() < 18) {
		        throw new UnderageException("Age: " + customer.getAge());
		    }
		    return new Beverage(5);
		}
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
		

		for(int j=0; j<20;j++) {
			Either<String,Double> value = compute().right().map(i -> i * 2).toEither();
			System.out.println("outcome: "+value+"\n");
		}
	}
	
	private Either<String, Double> compute() {
		double dice = new Random().nextDouble();
		System.out.println("dice: "+dice);
		return (dice > 0.5)? Right(dice):Left("error");
		
	}
	
	@Test public void validations() {
		PersonValidator personValidator = new PersonValidator();

		// Valid(Person(John Doe, 30))
		Validation<Seq<String>, Person> valid = personValidator.validatePerson("John Doe", 30);
		System.out.println(valid);
		assertThat(valid).isInstanceOf(Validation.Valid.class);
		assertThat(valid.get()).isInstanceOf(Person.class);

		// Invalid(List(Name contains invalid characters: '!4?', Age must be greater than 0))
		Validation<Seq<String>, Person> invalid = personValidator.validatePerson("John? Doe!4", -1);
		System.out.println(invalid);
		assertThat(invalid).isInstanceOf(Validation.Invalid.class);
		
		Throwable thrown = catchThrowable(() -> {
			// Boom!
			invalid.get();
		});

		assertThat(thrown).isInstanceOf(NoSuchElementException.class).hasMessage("get of 'invalid' Validation");

	}
	
	@Test public void collections() {
		for (double nonRandom : Stream.range(1, 20)) {
		    System.out.println(nonRandom);
		}
		
		for (double random : Stream.continually(Math::random).take(7)) {
		    System.out.println(random);
		}
		
		//Java ways
		int sum = Arrays.asList(1, 2, 3).stream().reduce((i, j) -> i + j).get();
		assertThat(sum).isEqualTo(6);
		
		sum = IntStream.of(1, 2, 3).sum();
		
		//Vavr way
		Number nsum = List.of(1, 2, 3).sum();
		assertThat(nsum.doubleValue()).isEqualTo(6d);
		assertThat(nsum.intValue()).isEqualTo(6);
		assertThat(nsum.floatValue()).isEqualTo(6f);

		//Stream in Vavr
		Stream<Integer> str = Stream.from(1).filter(i -> i % 2 == 0).take(3);
		assertThat(str.size()==3);
		assertThat(str.get(1)==4);
		
		Throwable thrown = catchThrowable(() -> {
			// Boom!
			str.get(3);
		});

		assertThat(thrown).isInstanceOf(IndexOutOfBoundsException.class).hasMessage("get(3) on Stream of size 3");
	
	}
	

	@Test public void match() {
		
		int i = 2;
		
		String s = Match(i).of(
			    Case($(1), "one"),
			    Case($(2), "two"),
			    Case($(), "?")
			);
		
		assertThat(s).isEqualTo("two");
		assertThat(s).isNotEqualTo("one");
		
		Option<String> sopt = Match(i).option(
			    Case($(0), "zero")
			);
		
		assertThat(sopt).isInstanceOf(None.class);
	}
	
	@Test public void match1() {
		
		String arg = "--version";
		
		Object r = Match(arg).of(
			    Case($(isIn("-h", "--help")), o -> run(this::displayHelp)),
			    Case($(isIn("-v", "--version")), o -> run(this::displayVersion)),
			    Case($(), o -> run(() -> {
			        throw new IllegalArgumentException(arg);
			    }))
			);
		
		System.out.println("match: " +r);
	}
	
	@Test public void match2() {
		
		Number obj = 1d;
		
		Number plusOne = Match(obj).of(
			    Case($(instanceOf(Integer.class)), i -> i + 1),
			    Case($(instanceOf(Double.class)), d -> d + 1),
			    Case($(), o -> { throw new NumberFormatException(); })
			);
		
		System.out.println("match: " + plusOne);
	}
	
	
	private void displayHelp() {System.out.println("This is a help message.");}
	private void displayVersion() {System.out.println("This is a version message.");}
	
	
	
	@Test public void propertyChecking() {

		
		Arbitrary<Integer> ints = Arbitrary.integer();
		Property.def("square(int) >= 0")
        .forAll(ints)
        .suchThat(i -> i * i >= 0)
        .check()
        .assertIsSatisfied();
	}
	
	class Person {

	    public final String name;
	    public final int age;

	    public Person(String name, int age) {
	        this.name = name;
	        this.age = age;
	    }

	    @Override
	    public String toString() {
	        return "Person(" + name + ", " + age + ")";
	    }

	}
	
	class PersonValidator {

	    private static final String VALID_NAME_CHARS = "[a-zA-Z ]";
	    private static final int MIN_AGE = 0;

	    public Validation<Seq<String>, Person> validatePerson(String name, int age) {
	        return Validation.combine(validateName(name), validateAge(age)).ap(Person::new);
	    }

	    private Validation<String, String> validateName(String name) {
	    	
	    	System.out.println("transformation: " +CharSeq.of(name).replaceAll(VALID_NAME_CHARS, "").transform(seq -> seq.isEmpty()));
	        
	    	return CharSeq.of(name).replaceAll(VALID_NAME_CHARS, "").transform(seq -> seq.isEmpty()

	                ? Validation.valid(name)
	                : Validation.invalid("Name contains invalid characters: '"
	                + seq.distinct().sorted() + "'"));
	    }

	    private Validation<String, Integer> validateAge(int age) {
	        return age < MIN_AGE
	                ? Validation.invalid("Age must be at least " + MIN_AGE)
	                : Validation.valid(age);
	    }

	}
	


	private Object somethingWithException(Exception t) {
		return t.getClass().getSimpleName();
		//return 1000;
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
	

	Try<java.util.List<String>> getSearchResults(String searchString){
		Try<java.util.List<String>>  t = null;
		try {
			throw new MalformedURLException("no urls");
		} catch (MalformedURLException e) {
			t = Try.failure(e);
		}
		return t;
		}

	
	
	@Test public void getSearchResultsTest() {
		String defaultResult = "http://www.google.com";
		java.util.List<String> urls = getSearchResults("javaday")
		.onFailure(ex -> logger.error("Houston, we have a problem: "+ex.getMessage()))
		.getOrElse((java.util.List<String>) Collections.singletonList(defaultResult)); //since something went wrong, we return default value
		assertThat(urls.size()==1);
		assertThat(urls.get(0)).isEqualTo(defaultResult);
	}
	
	java.util.List<String> getFromGoogle(String search) throws NoSuchElementException, IOException{
		throw new NoSuchElementException("the url doesn't exist");
	}
	
	java.util.List<String> getFromDuckDuckGo(String search) throws  IOException{
		throw new IOException("the url doesn't exist");
	}
	
	public static final class ForbiddenException extends RuntimeException {
		public ForbiddenException() {
			super("This site is forbidden");
		}
	}
	
	@SuppressWarnings({ "unchecked"})
	Try<java.util.List<String>> getSearchResults2Apis(String searchString){
		java.util.List<String> emptyList = Collections.EMPTY_LIST;
		return Try.of(()->getFromGoogle(searchString))
				.recover(NoSuchElementException.class, emptyList)
				.recover(ForbiddenException.class, emptyList)
				.orElse(Try.of(()->(getFromDuckDuckGo(searchString)))); //falback to duckDuckGo
		
	}
	
	@Test public void testGetSearchResultsWith2Apis() {
		Try<java.util.List<String>> result = getSearchResults2Apis("javaday");
				assertThat(result).isInstanceOf(Success.class);
				assertThat(result.get()).isEmpty();
		
	}
	
	Try<URL> getSingleSearchResults(String searchString){
		return Try.of(()->new URL("http://www.google.com"));
	}
	

	@Test
	public void testMultilevelTry() {
		Try<Try<InputStream>> multilevelTry = getSingleSearchResults("javaday").map(url -> Try.of(url::openStream));
		System.out.println(multilevelTry);
		Try<InputStream> singlelevelTry = getSingleSearchResults("javaday").flatMap(url -> Try.of(url::openStream));
		System.out.println(singlelevelTry);

		String expectedOutcome = "Opened successfully";
		
		String result = Match(singlelevelTry).of(
				Case($Success($()), expectedOutcome), 
				Case($Failure($()), "Failed..."));
		
		System.out.println(result);
		assertThat(result).isEqualTo(expectedOutcome);


	}
	
	public static final class FetchError extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public FetchError(String weight) {
			super(weight);
		}
		
	}
	
	public Either<FetchError, java.util.List<URL>> getSearchResults(Random random){
		Either<FetchError, java.util.List<URL>> result = null;

		java.util.List<URL> urls = new ArrayList<>();

			int dice = random.nextInt(10);
			if (dice<5) {//exception thrown
				result = Left(new FetchError(Integer.toString(dice)));
			} else {	//successful run
				try {

					for(int i=0; i<50;i=i+10) {
					urls.add(new URL("http://www."+(dice+i)+".com"));
					result = Right(urls);
					}
				} catch (MalformedURLException e) {
					result = Left(new FetchError(Integer.toString(dice)));
				}
			}

			return result;
		}

	
	@Test public void testEitherInErrorHandling() {
		Random random = new Random();
		for(int i=0;i<20;i++) {
			Either<FetchError, java.util.List<URL>> either = getSearchResults(random);
			System.out.println(either);
			//left projection handles errors
			either.left().map(error -> "received error with message" + error.getMessage()).forEach(System.out::println);
			//right projection handles values
			either .right().map(list -> "received list of URLs: " + list.toString()).forEach(System.out::println);

		}
	}

}
