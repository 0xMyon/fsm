package com.github.myon.fsmlib.factory;

import com.github.myon.fsmlib.container.Sequence;
import com.github.myon.fsmlib.immutable.ClosedSequence;

public interface SequenceFactory<O, B, T extends ClosedSequence<O, B, T>> extends ElementaryFactory<B, T>  {

	public T epsilon();

	@SuppressWarnings("unchecked")
	public default T sequence(final B... objects) {
		T result = this.epsilon();
		for (final B object: objects) {
			result = result.concat(this.element(object));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public default <R extends ClosedSequence<O, B, R>> T sequence(final R... others) {
		T result = this.epsilon();
		for (final R other: others) {
			result = result.concat(other.convert(this));
		}
		return result;
	}

	public default <R extends ClosedSequence<O, B, R>> T sequence(final Sequence<R> others) {
		return others.aggregate(
				()->this.epsilon(),
				(c,r)->r.concat(c.convert(SequenceFactory.this))
				);
	}

}
