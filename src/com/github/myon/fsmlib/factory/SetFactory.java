package com.github.myon.fsmlib.factory;

import com.github.myon.fsmlib.immutable.ClosedSet;

public interface SetFactory<O, B, T extends ClosedSet<O ,B, T>> extends ElementaryFactory<B, T> {

	public T empty();

	@SuppressWarnings("unchecked")
	public default T union(final B... objects) {
		T result = this.empty();
		for (final B object: objects) {
			result = result.union(this.element(object));
		}
		return result;
	}


}