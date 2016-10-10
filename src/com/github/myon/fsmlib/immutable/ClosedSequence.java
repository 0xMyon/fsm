package com.github.myon.fsmlib.immutable;

import com.github.myon.fsmlib.container.Sequence;
import com.github.myon.fsmlib.factory.ElementaryFactory;

/**
 *
 * @author 0xMyon
 *
 * @param <O> element type
 * @param <T> underling type
 */
public interface ClosedSequence<O, B, T extends ClosedSequence<O, B, T>> {

	/**
	 * concatenates two sequences
	 * @param that
	 * @return a sequence
	 */
	public T concat(T that);

	/**
	 *
	 * @return true, if the sequence is finite
	 */
	public boolean isFinite();

	/**
	 *
	 * @return true, if the sequence is epsilon
	 */
	public boolean isEpsilon();


	public Factory<O, B, T> factory();

	public <R extends ClosedSequence<O, B, R>> R convert(final Factory<O, B, R> factory);


	public static interface Factory<O, B, T extends ClosedSequence<O, B, T>> extends ElementaryFactory<B, T>  {

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
					(c,r)->r.concat(c.convert(Factory.this))
					);
		}

	}

}
