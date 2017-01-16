package com.github.myon.util;

import java.util.Objects;
import java.util.function.Function;

public class Tuple<F,S> extends Anything {

	public final F source;
	public final S target;

	private Tuple(final F source, final S target) {
		this.source = source;
		this.target = target;
	}

	public <T> Tuple<T,S> mapLeft(final Function<? super F, T> function) {
		return Tuple.of(function.apply(this.source), this.target);
	}

	public <T> Tuple<F,T> mapRight(final Function<? super S, T> function) {
		return Tuple.of(this.source, function.apply(this.target));
	}

	public static <F,S> Tuple<F,S> of(final F first, final S second) {
		return new Tuple<>(first, second);
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
