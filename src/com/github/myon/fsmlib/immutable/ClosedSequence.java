package com.github.myon.fsmlib.immutable;

import com.github.myon.fsmlib.factory.SequenceFactory;

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


	public SequenceFactory<O, B, T> factory();

	public <R extends ClosedSequence<O, B, R>> R convert(final SequenceFactory<O, B, R> factory);


}
