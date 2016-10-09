package com.github.myon.fsmlib.factory;

import com.github.myon.fsmlib.immutable.ClosedSequence;

public interface SequenceFactory<O, B, T extends ClosedSequence<O, B, T>> extends ElementaryFactory<B, T>  {

	@SuppressWarnings("unchecked")
	public default T sequence(final B... objects) {
		T result = this.epsilon();
		for (final B object: objects) {
			result = result.concat(this.element(object));
		}
		return result;
	}

	public T epsilon();



}
