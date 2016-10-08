package com.github.myon.fsmlib.mutable;

import com.github.myon.fsmlib.immutable.ClosedSequence;

/**
 * @author 0xMyon
 *
 * a mutable sequence
 * @param <O> element type
 * @param <T> underling type
 */
public interface MutableSequence<O, T extends MutableSequence<O, T>> extends ClosedSequence<O, T> {

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

}
