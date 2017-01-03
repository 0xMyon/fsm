package com.github.myon.fsmlib.expression;

import com.github.myon.fsmlib.immutable.ClosedLanguage;

/**
 * @author 0xMyon
 *
 * iteration expression
 * @param <O> based object type
 * @param <T> underlying type
 */
public class IterationExpression<O, T extends ClosedLanguage<O, O, T, ?>> extends Expression<O,T> {

	private final Expression<O,T> one;

	private IterationExpression(final ClosedLanguage.Factory<O, O, T, ?> factory, final Expression<O,T> one) {
		super(factory);
		this.one = one;
	}

	public static <O, T extends ClosedLanguage<O, O, T, ?>> IterationExpression<O,T> create(final ClosedLanguage.Factory<O, O, T, ?> factory, final Expression<O,T> one) {
		// iteration is idendenpotent
		if (one instanceof IterationExpression) {
			return (IterationExpression<O,T>) one;
		} else {
			return new IterationExpression<>(factory, one);
		}
	}

	@Override
	public <R extends ClosedLanguage<O, O, R, ?>> R convert(final ClosedLanguage.Factory<O, O, R, ?> factory) {
		return this.one.convert(factory).iteration();
	}

}
