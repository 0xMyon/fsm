package com.github.myon.fsmlib.expression;

import com.github.myon.fsmlib.factory.LanguageFactory;
import com.github.myon.fsmlib.immutable.ClosedLanguage;

/**
 * @author 0xMyon
 *
 * iteration expression
 * @param <O> based object type
 * @param <T> underlying type
 */
public class IterationExpression<O, T extends ClosedLanguage<O, O, T>> extends Expression<O,T> {

	private final Expression<O,T> one;

	private IterationExpression(final LanguageFactory<O, O, T> factory, final Expression<O,T> one) {
		super(factory);
		this.one = one;
	}

	public static <O, T extends ClosedLanguage<O, O, T>> Expression<O,T> create(final LanguageFactory<O, O, T> factory, final Expression<O,T> one) {
		if (one instanceof IterationExpression) {
			return one;
		} else {
			return new IterationExpression<>(factory, one);
		}
	}

	@Override
	public <R extends ClosedLanguage<O, O, R>> R convert(final LanguageFactory<O, O, R> factory) {
		return this.one.convert(factory).iteration();
	}

}
