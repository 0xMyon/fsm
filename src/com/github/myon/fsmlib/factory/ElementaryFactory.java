package com.github.myon.fsmlib.factory;

public interface ElementaryFactory<B, T extends Object> {

	public T element(B object);

}
