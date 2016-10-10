package com.github.myon.fsmlib.mutable;

import com.github.myon.fsmlib.container.Sequence;
import com.github.myon.fsmlib.immutable.ClosedLanguage;

public interface MutableLanguage<O, B, T extends MutableLanguage<O, B, T, F>, F extends MutableLanguage.Factory<O, B, T, F>> extends
MutableSymetricSet<Sequence<O>, B, T, F>, MutableSequence<O, B, T, F>, ClosedLanguage<O, B, T, F> {

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


	public static interface Factory<O,B,T extends MutableLanguage<O, B, T, F>, F extends Factory<O,B,T,F>> extends
	MutableSymetricSet.Factory<Sequence<O>, B, T, F>,
	MutableSequence.Factory<O, B, T, F>,
	ClosedLanguage.Factory<O, B, T, F> {

	}


}
