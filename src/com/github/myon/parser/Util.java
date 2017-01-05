package com.github.myon.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class Util {

	@SafeVarargs
	public static <T> Set<T> set(final T... elements) {
		final Set<T> result = new HashSet<>();
		for(final T elememt: elements) {
			result.add(elememt);
		}
		return result;
	}

	@SafeVarargs
	public static <T> Set<T> set(final Set<T>... elements) {
		final Set<T> result = new HashSet<>();
		for(final Set<T> elememt: elements) {
			result.addAll(elememt);
		}
		return result;
	}

	public static <T,R> Set<R> map(final Set<T> set, final Function<T,R> function) {
		final Set<R> result = new HashSet<>();
		for(final T elememt: set) {
			result.add(function.apply(elememt));
		}
		return result;
	}

	public static <T,R> Set<R> aggregate(final Set<T> set, final Function<T,Set<R>> function) {
		final Set<R> result = new HashSet<>();
		for(final T elememt: set) {
			result.addAll(function.apply(elememt));
		}
		return result;
	}

	public static List<Character> string(final String str) {
		final List<Character> result = new ArrayList<>();
		for(int i=0;i<str.length();i++) {
			result.add(str.charAt(i));
		}
		return result;
	}

	public static <T> Set<T> filter(final Set<T> set, final Predicate<T> predicate) {
		final Set<T> result = Util.set();
		for(final T current: set) {
			if (predicate.test(current)) {
				result.add(current);
			}
		}
		return result;
	}

	public static <T> List<T> list(final T first, final List<T> rest) {
		final List<T> result = new LinkedList<>();
		result.add(first);
		result.addAll(rest);
		return result;
	}

	@SafeVarargs
	public static <T> List<T> list(final T... elements) {
		final List<T> result = new LinkedList<>();
		for(final T element : elements) {
			result.add(element);
		}
		return result;
	}


}
