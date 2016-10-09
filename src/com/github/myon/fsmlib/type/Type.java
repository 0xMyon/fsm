package com.github.myon.fsmlib.type;

import java.util.function.Predicate;

/**
 * @author 0xMyon
 *
 * basic type interface
 * @param <O> super-type
 */
public interface Type<O> extends Predicate<O> {

	/**
	 * checks, if an object is contained
	 * @param object the checked objects
	 * @return true, if objects is contained
	 */
	public boolean contains(O object);


	@Override
	public default boolean test(final O object) {
		return this.contains(object);
	}

}
