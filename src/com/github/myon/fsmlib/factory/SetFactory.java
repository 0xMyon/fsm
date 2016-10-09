package com.github.myon.fsmlib.factory;

import com.github.myon.fsmlib.immutable.ClosedSet;

public interface SetFactory<O, B, T extends ClosedSet<O ,B, T>> {

	@SuppressWarnings("unchecked")
	public T union(final B... objects);


}
