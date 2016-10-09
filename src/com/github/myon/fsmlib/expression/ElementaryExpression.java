package com.github.myon.fsmlib.expression;

import com.github.myon.fsmlib.immutable.ClosedLanguage;

/**
 * @author 0xMyon
 *
 * elementary expression
 * @param <O> based object type
 * @param <T> underlying type
 */
public class ElementaryExpression<O, T extends ClosedLanguage<O, T>> extends Expression<O, T> {

	private final T type;

	public ElementaryExpression(final T type) {
		this.type = type;
	}

	@Override
	public T evaluate() {
		return this.type;
	}

}
