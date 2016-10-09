package com.github.myon.fsmlib.immutable;

import com.github.myon.fsmlib.factory.SetFactory;
import com.github.myon.fsmlib.factory.SymetricSetFactory;

/**
 * @author 0xMyon
 *
 * closed set, that may be inverted
 * @param <O> super type
 * @param <T> underling type
 */
public interface ClosedSymetricSet<O, B, T extends ClosedSymetricSet<O, B, T>> extends ClosedSet<O, B, T> {

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

	@Override
	public SymetricSetFactory<O, B, T> factory();

	@Override
	public default <R extends ClosedSet<O, B, R>> R convert(final SetFactory<O, B, R> factory) {
		throw new Error();
	}


	public <R extends ClosedSymetricSet<O, B, R>> R convert(final SymetricSetFactory<O, B, R> factory);

}
