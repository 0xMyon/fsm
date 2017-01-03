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
		return (this.a==null?0:this.a.hashCode()) + (this.b==null?0:this.b.hashCode());
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof Pair) {
			final Pair<?> that = (Pair<?>) other;
			return Anything.equals(this.a,that.a) && Anything.equals(this.b,that.b)
					|| Anything.equals(this.a,that.b) && Anything.equals(this.b,that.a);
		}
		return false;
	}


}
