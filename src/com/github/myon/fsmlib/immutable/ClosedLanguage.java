package com.github.myon.fsmlib.immutable;

import com.github.myon.fsmlib.container.Sequence;
import com.github.myon.fsmlib.factory.LanguageFactory;
import com.github.myon.fsmlib.factory.SequenceFactory;
import com.github.myon.fsmlib.factory.SymetricSetFactory;

/**
 *
 * @author 0xMyon
 *
 * a language is a set of sequences
 * @param <O> underling type
 * @param <T> sub type
 */
public interface ClosedLanguage<O, B, T extends ClosedLanguage<O,B,T>> extends ClosedSymetricSet<Sequence<O>, B, T>, ClosedSequence<O, B, T> {

	/**
	 * creates an iterated language
	 * @return
	 */
	public T iteration();

	/**
	 * creates an optional language
	 */
	public T option();


	@Override
	public LanguageFactory<O, B, T> factory();

	@Override
	public default <R extends ClosedSymetricSet<Sequence<O>, B, R>> R convert(final SymetricSetFactory<Sequence<O>, B, R> factory) {
		throw new Error();
	}

	@Override
	public default <R extends ClosedSequence<O, B, R>> R convert(final SequenceFactory<O,B, R> factory) {
		throw new Error();
	}

	public <R extends ClosedLanguage<O, B, R>> R convert(final LanguageFactory<O,B, R> factory);


}
