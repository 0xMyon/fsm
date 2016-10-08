package com.github.myon.fsmlib;

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
	 * iterates the language
	 * @return
	 */
	public T itterate();

	/**
	 * creates an optional language
	 * @return
	 */
	public T optional();

}
