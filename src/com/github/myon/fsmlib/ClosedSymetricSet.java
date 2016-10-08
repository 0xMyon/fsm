package com.github.myon.fsmlib;

/**
 * @author 0xMyon
 *
 * closed set, that may be inverted
 * @param <O> super type
 * @param <T> underling type
 */
public interface ClosedSymetricSet<O, T extends ClosedSymetricSet<O, T>> extends ClosedSet<O, T> {

	/**
	 * inverts the type
	 * @return a type, that contains all objects that are not contained in this type
	 */
	T invert();

	@Override
	default T intersect(final T that) {
		return this.invert().unite(that.invert()).invert();
	}

	@Override
	default T minus(final T that) {
		return this.invert().unite(that).invert();
	}

}
