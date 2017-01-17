package com.github.myon.util;

import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public class Tuple<F,S> extends Anything {

	public final F source;
	public final S target;

	private Tuple(final F source, final S target) {
		this.source = source;
		this.target = target;
	}

	public static <F,S,T> Function<Tuple<F,S>, Tuple<T,S>> left(final Function<? super F,T> function) {
		return tuple -> Tuple.of(function.apply(tuple.source), tuple.target);
	}

	public static <F,S> Tuple<F,S> of(final F first, final S second) {
		return new Tuple<>(first, second);
	}

	public static <T> Stream<T> merge(final Tuple<T,Stream<T>> tuple) {
		return Stream.concat(Stream.of(tuple.source), tuple.target);
	}

	public Tuple<S,F> swop() {
		return Tuple.of(this.target, this.source);
	}

	public F source() {
		return this.source;
	}

	public S target() {
		return this.target;
	}

	@Override
	public String toString() {
		return "("+this.source+";"+this.target+")";
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.source,this.target);
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof Tuple) {
			final Tuple<?,?> that = (Tuple<?,?>) other;
			return this.source.equals(that.source) && this.target.equals(that.target);
		}
		return false;
	}

}
