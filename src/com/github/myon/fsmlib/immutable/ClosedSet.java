package com.github.myon.fsmlib.immutable;

import com.github.myon.fsmlib.type.OrderedType;

/**
 * @author 0xMyon
 *
 * ordered type, that may be composed and checked for size
 * @param <O> super type
 * @param <T> underlying type
 */
public interface ClosedSet<O, T extends ClosedSet<O, T>> extends OrderedType<O, T> {

	/**
	 * unites two types
	 * all types that are empty, are neutral elements
	 * @param that
	 * @return a type that contains all objects of this or that
	 */
	public T union(T that);

	/**
	 * intersects two types
	 * all types that are empty, are total elements
	 * @param that
	 * @return a type that contains all objects of this and that
	 */
	public T intersection(T that);

	/**
	 * intersects two types
	 * @param that
	 * @return a type that contains all objects of this and not that
	 */
	public T minus(T that);

	/**
	 * checks if the type is empty
	 * @return true, if the type is empty
	 */
	public boolean isEmpty();

	/**
	 * checks if the type is finite
	 * @return true, if the type is finite
	 */
	public boolean isFinite();

	/**
	 * checks, if there are objects in both types
	 * @param that
	 * @return true, if there are objects in both types
	 */
	public default boolean isDisjunced(final T that) {
		return this.intersection(that).isEmpty();
	}

}
