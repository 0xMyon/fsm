package com.github.myon.fsmlib.mutable;

import com.github.myon.fsmlib.container.Sequence;
import com.github.myon.fsmlib.immutable.ClosedSequence;

/**
 * @author 0xMyon
 *
 * a mutable sequence
 * @param <O> element type
 * @param <T> underling type
 */
public interface MutableSequence<O, B, T extends MutableSequence<O, B, T>> extends ClosedSequence<O, B, T> {

	/**
	 * appends a sequence
	 * @param that
	 */
	public void append(T that);

	/**
	 * prepends a sequence
	 * @param that
	 */
	public void prepend(T that);

	/**
	 * appends an objects
	 * @param object
	 */
	public void append(O object);

	/**
	 * prepends an objects
	 * @param object
	 */
	public void prepend(O object);

	@Override
	public default T concat(final T that) {
		final T result = this.copy();
		result.append(that);
		return result;
	}

	/**
	 * creates a copy
	 * @return
	 */
	public T copy();


	public interface Factory<O, B, T extends MutableSequence<O, B, T>> extends ClosedSequence.Factory<O, B, T> {

		@Override
		@SuppressWarnings("unchecked")
		public default T sequence(final B... objects) {
			final T result = this.epsilon();
			for (final B object: objects) {
				result.append(this.element(object));
			}
			return result;
		}

		@Override
		@SuppressWarnings("unchecked")
		public default <R extends ClosedSequence<O, B, R>> T sequence(final R... others) {
			final T result = this.epsilon();
			for (final R other: others) {
				result.append(other.convert(this));
			}
			return result;
		}

		@Override
		public default <R extends ClosedSequence<O, B, R>> T sequence(final Sequence<R> others) {
			final T result = this.epsilon();
			others.forAll((other)-> result.append(other.convert(this)));
			return result;
		}


	}

}
