package com.github.myon.fsmlib.expression;

import com.github.myon.fsmlib.immutable.ClosedLanguage;

/**
 * @author 0xMyon
 *
 * iteration expression
 * @param <O> based object type
 * @param <T> underlying type
 */
public class IterationExpression<O, T extends ClosedLanguage<O, T>> extends Expression<O,T> {

	private final Expression<O,T> one;

	private IterationExpression(final Expression<O,T> one) {
		this.one = one;
	}

	public static <O, T extends ClosedLanguage<O, T>> Expression<O,T> create(final Expression<O,T> one) {
		if (one instanceof IterationExpression) {
			return one;
		} else {
			return new IterationExpression<>(one);
		}
	}

	@Override
	public T evaluate() {
		return this.one.evaluate().iteration();
	}

}
