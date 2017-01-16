package com.github.myon.parser;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.github.myon.util.Tuple;

/**
 *
 * A Parser converts a {@code List} of input to a {@code Set} of output.
 * The output {@code Tuple} consists of the actual result and the not processed {@code List} of inputs.
 *
 * @author 0xMyon
 *
 * @param <I> the input symbols
 * @param <O> the actual output
 */
public interface Parser<I,O> extends Function<List<I>, Stream<Tuple<O,List<I>>>> {

	// TODO use Streams everywhere


	/**
	 * @return a {@code Parser<I,O>} always returning an empty {@code Set}
	 */
	public static <I,O> Parser<I,O> nothing() {
		return word -> Stream.empty();
	}

	/**
	 * @param supplier {@code Supplier<O>} of the result
	 * @return a {@code Parser<I,O>} whose output is supplied external
	 */
	public static <I,O> Parser<I,O> succeed(final Supplier<? extends O> supplier) {
		return word -> Stream.of(Tuple.of(supplier.get(), word));
	}

	/**
	 * @param predicate {@code Predicate<IO>}
	 * @return a {@code Parser<IO,IO>} that reads the first input and test it against the provided predicate
	 */
	public static <IO> Parser<IO,IO> satisfy(final Predicate<? super IO> predicate) {
		return word ->
		!word.isEmpty() && predicate.test(word.get(0)) ?
				Stream.of(Tuple.of(word.get(0), word.subList(1, word.size())))
				: Stream.empty();
	}

	/**
	 * unites the result set of two parsers
	 * @param that
	 * @return a {@code Parser<I,O>}
	 */
	public default Parser<I,O> choice(final Parser<I,O> that) {
		return word -> Stream.concat(this.apply(word), that.apply(word));
	}

	@SafeVarargs
	public static <I,O> Parser<I,O> choice(final Parser<I, O>... parsers) {
		return Stream.of(parsers).reduce((a,b) -> a.choice(b)).orElseGet(Parser::nothing);
	}

	/**
	 *
	 * @param that
	 * @return
	 */
	public default <T> Parser<I,Tuple<O,T>> concat(final Parser<I,T> that) {
		return word -> this.apply(word)
				.map(a -> that.apply(a.target)
						.map(Tuple.left(s -> Tuple.of(a.source, s))))
				.reduce(Stream.empty(), Stream::concat);
	}

	/**
	 * applies a {@code Function<O,T>} to the result set
	 * @param function the {@code Function} that is applied to every single result
	 * @return a {@code Parser<I,T>} with converted output
	 */
	public default <T> Parser<I,T> map(final Function<? super O, T> function) {
		return word -> this.apply(word).map(Tuple.left(function));
	}

	/**
	 * filters the result set by {@code BiPredicate}
	 * @param predicate {@code BiPredicate} to filter the result set
	 * @return a {@code Parser<I,O>} with filtered output
	 */
	public default Parser<I,O> filter(final Predicate<? super Tuple<O, List<I>>> predicate) {
		return word -> this.apply(word).filter(predicate);
	}

	/**
	 * filters the result set for the advanced parsed results
	 * @return
	 */
	public default Parser<I,O> greedy() {
		return word -> this.apply(word)
				.min((a,b) -> a.target.size() - b.target.size())
				.map(Stream::of).orElseGet(Stream::empty);
	}


	/**
	 * creates a {@code Parser<I,O>} that succeeds with an empty {@code List}
	 * @return
	 */
	public static <I,O> Parser<I,List<O>> epsilon() {
		return Parser.succeed(LinkedList::new);
	}

	/**
	 * creates a {@code Parser<IO,IO>} that is satisfied with a class
	 * @param clazz the satisfying {@code Class}
	 * @return
	 */
	public static <IO> Parser<IO,IO> satisfy(final Class<? super IO> clazz) {
		return Parser.satisfy(clazz::isInstance);
	}

	/**
	 * creates a {@code Parser<IO,IO>} that is satisfied with an object
	 * @param object the satisfying {@code IO::equals}
	 * @return
	 */
	public static <IO> Parser<IO,IO> satisfy(final IO object) {
		return Parser.satisfy(object::equals);
	}


	/**
	 * creates a sequential {@code Parser<I,List<O>>}
	 * @param elements
	 * @return a {@code Parser} that aggregates results of parsers in a list
	 */
	@SafeVarargs
	public static <I,O> Parser<I,List<O>> sequence(final Parser<I,O>... elements) {
		Parser<I,List<O>> result = Parser.epsilon();
		for(int i = elements.length-1; i >= 0; i++) {
			result = elements[i].prepend(result);
		}
		return result;
	}

	public default Parser<I,List<O>> prepend(final Parser<I,List<O>> that) {
		return this.concat(that).map(Tuple::merge);
	}

	/**
	 * parses any number of elements into a {@code List}
	 * @return none-greedy {@code Parser}
	 */
	public default Parser<I, List<O>> any() {
		return Parser.recursive( self -> this.prepend(self).choice(Parser.epsilon()) );
	}

	public default Parser<I, List<O>> many() {
		return this.prepend(this.any());
	}

	public default Parser<I, Optional<O>> option() {
		return this.map(Optional::of).choice(Parser.succeed(Optional::empty));
	}

	public default <T> Parser<I,O> left(final Parser<I,T> that) {
		return this.concat(that).map(Tuple::source);
	}

	public default <T> Parser<I,T> right(final Parser<I,T> that) {
		return this.concat(that).map(Tuple::target);
	}



	/**
	 * filters the result set for completely consumed input
	 * @return
	 */
	public default Parser<I,O> just() {
		return this.filter(result -> result.target.isEmpty());
	}



	public default Parser<I, List<O>> whole() {
		return this.many().greedy();
	}

	public default Parser<I, List<O>> all() {
		return this.any().greedy();
	}

	public default Parser<I, Optional<O>> consume() {
		return this.option().greedy();
	}


	public static <I,O> Parser<I,O> recursive(final Function<Parser<I,O>,Parser<I,O>> r) {
		return ((RecursiveFunction<Parser<I,O>>) f -> f.apply(f))
				.apply(f -> r.apply(x -> f.apply(f).apply(x)));
	}

}
