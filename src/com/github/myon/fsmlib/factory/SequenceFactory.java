package com.github.myon.fsmlib.factory;

import com.github.myon.fsmlib.immutable.ClosedSequence;

public interface SequenceFactory<O, B, T extends ClosedSequence<O, B, T>>  {

	@SuppressWarnings("unchecked")
	public T sequence(final B... objects);

}
