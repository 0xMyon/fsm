package com.github.myon.fsmlib.immutable;

import com.github.myon.fsmlib.FiniteSequence;

/**
 *
 * @author 0xMyon
 *
 * a language is a set of sequences
 * @param <O> underling type
 * @param <T> sub type
 */
public interface ClosedLanguage<O, T extends ClosedLanguage<O,T>> extends ClosedSymetricSet<FiniteSequence<O>, T>, ClosedSequence<O, T> {

	/**
	 * creates an iterated language
	 * @return
	 */
	public T iteration();

	/**
	 * creates an optional language
	 * @return
	 */
	public T option();

}
