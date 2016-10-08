package com.github.myon.fsmlib.immutable;

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
	T complement();

	@Override
	default T intersection(final T that) {
		return this.complement().union(that.complement()).complement();
	}

	@Override
	default T minus(final T that) {
		return this.complement().union(that).complement();
	}

}
