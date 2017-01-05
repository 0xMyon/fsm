package com.github.myon.util;

/**
 *
 * @author 0xMyon
 *
 * order independent pair
 * @param <T>
 */
public class Pair<T> extends Anything {

	public final T first,second;

	public Pair(final T a, final T b) {
		this.first = a;
		this.second = b;
	}

	@Override
	public String toString() {
		return "("+this.first+","+this.second+")";
	}

	@Override
	public int hashCode() {
		return (this.first==null?0:this.first.hashCode()) + (this.second==null?0:this.second.hashCode());
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof Pair) {
			final Pair<?> that = (Pair<?>) other;
			return Anything.equals(this.first,that.first) && Anything.equals(this.second,that.second)
					|| Anything.equals(this.first,that.second) && Anything.equals(this.second,that.first);
		}
		return false;
	}


}
