package com.github.myon.fsmlib.mutable;

import com.github.myon.fsmlib.container.Sequence;
import com.github.myon.fsmlib.immutable.ClosedLanguage;

public interface MutableLanguage<O, B, T extends MutableLanguage<O, B, T>> extends MutableSymetricSet<Sequence<O>, B, T>, MutableSequence<O, B, T>, ClosedLanguage<O, B, T> {

	@Override
	public default T iteration() {
		final T result = this.copy();
		result.iterate();
		return result;
	}


	@Override
	public default T option() {
		final T result = this.copy();
		result.add(new Sequence<>());
		return result;
	}

	/**
	 * iterates the language
	 */
	public void iterate();


	public static interface Factory<O,B,T extends MutableLanguage<O, B, T>> extends
	MutableSymetricSet.Factory<Sequence<O>, B, T>,
	MutableSequence.Factory<O, B, T>,
	ClosedLanguage.Factory<O, B, T> {

	}


}
