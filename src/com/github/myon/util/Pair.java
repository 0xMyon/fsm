package com.github.myon.util;

/**
 *
 * @author 0xMyon
 *
 * order independent pair
 * @param <T>
 */
public class Pair<T> extends Anything {

	public final T a,b;

	public Pair(final T a, final T b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public String toString() {
		return "("+this.a+","+this.b+")";
	}

	@Override
	public int hashCode() {
		return this.a.hashCode() + this.b.hashCode();
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof Pair) {
			final Pair<?> that = (Pair<?>) other;
			return this.a.equals(that.a) && this.b.equals(that.b) || this.a.equals(that.b) && this.b.equals(that.a);
		}
		return false;
	}



}
