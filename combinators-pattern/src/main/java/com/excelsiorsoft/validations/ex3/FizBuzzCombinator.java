/**
 * 
 */
package com.excelsiorsoft.validations.ex3;

import static com.excelsiorsoft.validations.ex3.FizBuzzCombinator.NumberToWord.buzz;
import static com.excelsiorsoft.validations.ex3.FizBuzzCombinator.NumberToWord.fizz;
import static com.excelsiorsoft.validations.ex3.FizBuzzCombinator.NumberToWord.fizzBuzz;
import static com.excelsiorsoft.validations.ex3.FizBuzzCombinator.NumberToWord.number;

import java.util.function.Function;

/**
 * @author Simeon
 *
 */
public class FizBuzzCombinator {

	static Function<Integer, String> withCombinator() {
		return fizzBuzz().orElse(fizz()).orElse(buzz())
//	      .orElse(word("Bizz").ifDivisibleBy(4))
				.orElse(number());
	}

	interface NumberToWord extends Function<Integer, String> {

		static NumberToWord fizzBuzz() {
			return fizz().and(buzz());
		}

		static NumberToWord fizz() {
			return word("Fizz").ifDivisibleBy(3);
		}

		static NumberToWord buzz() {
			return word("Buzz").ifDivisibleBy(5);
		}

		static NumberToWord word(String word) {
			return i -> word;
		}

		static NumberToWord number() {
			return Object::toString;
		}

		default NumberToWord ifDivisibleBy(int modulo) {
			return i -> i % modulo == 0 ? apply(i) : "";
		}

		default NumberToWord orElse(NumberToWord other) {
			return i -> {
				String result = apply(i);
				return result.isEmpty() ? other.apply(i) : result;
			};
		}

		default NumberToWord and(NumberToWord other) {
			return i -> {
				String first = this.apply(i);
				String second = other.apply(i);
				return (first.isEmpty() || second.isEmpty()) ? "" : (first + second);
			};
		}
	}

}
