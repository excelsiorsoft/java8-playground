package com.excelsiorsoft.provided;

/**
 * @author Simeon
 *
 */
import java.util.function.Function;
import java.util.function.Predicate;

@FunctionalInterface
public interface Provided<T, R>
		extends Function<Predicate<T>, Function<Function<T, R>, Function<Function<T, R>, Function<T, R>>>> {

	static <T, R> Provided<T, R> create() {
		return condition -> function -> fallback -> arg -> (condition.test(arg) ? function : fallback).apply(arg);
	}

	static <T, R> Function<T, R> decorate(Predicate<T> condition, Function<T, R> function, Function<T, R> fallback) {
		return Provided.<T, R>create().apply(condition).apply(function).apply(fallback);
	}
}