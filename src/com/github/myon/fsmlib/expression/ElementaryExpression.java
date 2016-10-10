package com.github.myon.fsmlib.expression;

import com.github.myon.fsmlib.immutable.ClosedLanguage;

/**
 * @author 0xMyon
 *
 * elementary expression
 * @param <O> based object type
 * @param <T> underlying type
 */
public class ElementaryExpression<O, T extends ClosedLanguage<O, O, T>> extends Expression<O, T> {

	private final O object;

	public ElementaryExpression(final ClosedLanguage.Factory<O, O, T> factory, final O object) {
		super(factory);
		this.object = object;
	}

	@Override
	public <R extends ClosedLanguage<O, O, R>> R convert(final ClosedLanguage.Factory<O, O, R> factory) {
		return factory.element(this.object);
	}













}
