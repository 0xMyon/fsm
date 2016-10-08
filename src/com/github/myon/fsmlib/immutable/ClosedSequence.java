package com.github.myon.fsmlib.immutable;

/**
 *
 * @author 0xMyon
 *
 * @param <O> element type
 * @param <T> underling type
 */
public interface ClosedSequence<O, T extends ClosedSequence<O, T>> {

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


}
