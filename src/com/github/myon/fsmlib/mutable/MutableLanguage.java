package com.github.myon.fsmlib.mutable;

import com.github.myon.fsmlib.container.Sequence;
import com.github.myon.fsmlib.immutable.ClosedLanguage;

public interface MutableLanguage<O, T extends MutableLanguage<O, T>> extends MutableSymetricSet<Sequence<O>, T>, MutableSequence<O, T>, ClosedLanguage<O, T> {

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

}
