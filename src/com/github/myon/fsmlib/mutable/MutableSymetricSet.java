package com.github.myon.fsmlib.mutable;

import com.github.myon.fsmlib.immutable.ClosedSymetricSet;

/**
 * @author 0xMyon
 *
 * a mutable symetric set
 * @param <O> super type
 * @param <T> underling type
 */
public interface MutableSymetricSet<O, T extends MutableSymetricSet<O, T>> extends MutableSet<O, T>, ClosedSymetricSet<O, T> {

	@Override
	default T intersection(final T that) {
		return MutableSet.super.intersection(that);
	}

	@Override
	default T minus(final T that) {
		return MutableSet.super.minus(that);
	}

	/**
	 * inverts the set
	 */
	public void invert();

	@Override
	default T complement() {
		final T result = this.copy();
		result.invert();
		return result;
	}

	@Override
	default void subtract(final T that) {
		this.invert();
		this.unite(that);
		this.invert();
	}


	@Override
	default void intersect(final T that) {
		this.subtract(that.complement());
	}


}
