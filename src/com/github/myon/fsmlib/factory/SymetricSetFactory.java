package com.github.myon.fsmlib.factory;

import com.github.myon.fsmlib.immutable.ClosedSymetricSet;

public interface SymetricSetFactory<O, B, T extends ClosedSymetricSet<O, B, T>> extends SetFactory<O, B, T> {

	@SuppressWarnings("unchecked")
	public T intersection(final B... objects);

}
