package com.github.myon.fsmlib.mutable;

import com.github.myon.fsmlib.immutable.ClosedSet;

/**
 * @author 0xMyon
 *
 * a mutable set
 * @param <O> super type
 * @param <T> underling type
 */
public interface MutableSet<O, T extends MutableSet<O, T>> extends ClosedSet<O, T> {

	/**
	 * removes all objects that are in that set
	 * @param that
	 */
	public void subtract(T that);

	/**
	 * adds all objects that are in that set
	 * @param that
	 */
	public void unite(T that);

	/**
	 * removes all objects that are not in that set
	 * @param that
	 */
	public void intersect(T that);

	/**
	 * removes an object
	 * @param object
	 */
	public void remove(final O object);

	/**
	 * adds an object
	 * @param object
	 */
	public void add(final O object);


	@Override
	public default T union(final T that) {
		final T result = this.copy();
		result.unite(that);
		return result;
	}

	@Override
	public default T intersection(final T that) {
		final T result = this.copy();
		result.intersect(that);
		return result;
	}

	@Override
	public default T minus(final T that) {
		final T result = this.copy();
		result.subtract(that);
		return result;
	}


	/**
	 * creates a copy
	 * @return
	 */
	public T copy();


}
