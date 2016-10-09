package com.github.myon.fsmlib.factory;

import com.github.myon.fsmlib.container.Sequence;
import com.github.myon.fsmlib.immutable.ClosedLanguage;

public interface LanguageFactory<O, B, T extends ClosedLanguage<O, B, T>> extends SequenceFactory<O, B, T>, SymetricSetFactory<Sequence<O>, B, T> {

	T element(B object);

}
