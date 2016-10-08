package com.github.myon.fsmlib;

/**
 * @author 0xMyon
 *
 * type, that is ordered partial
 * @param <O> super type
 * @param <T> underlying type
 */
public interface OrderedType<O, T extends OrderedType<O, T>> extends Type<O> {

	/**
	 * compares the order of two underlying types
	 * returns true, if for all objects that.contains(object) == true, this.contains(object) == true
	 * @param that sub type
	 * @return true, if this contains all objects of that
	 */
	public boolean containsAll(T that);

}
