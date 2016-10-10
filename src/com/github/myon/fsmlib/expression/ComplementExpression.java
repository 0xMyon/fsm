package com.github.myon.fsmlib.expression;

import com.github.myon.fsmlib.immutable.ClosedLanguage;

/**
 * @author 0xMyon
 *
 * complement expression
 * @param <O> based object type
 * @param <T> underlying type
 */
public class ComplementExpression<O, T extends ClosedLanguage<O, O, T, ?>> extends Expression<O,T> {

	private final Expression<O,T> complement;

	private ComplementExpression(final ClosedLanguage.Factory<O, O, T, ?> factory,  final Expression<O,T> complement) {
		super(factory);
		this.complement = complement;
	}

	public static <O, T extends ClosedLanguage<O, O, T, ?>> Expression<O,T> create(final ClosedLanguage.Factory<O, O, T, ?> factory, final Expression<O,T> complement) {
		if (complement instanceof ComplementExpression) {
			return ((ComplementExpression<O,T>) complement).complement;
		} else {
			return new ComplementExpression<>(factory, complement);
		}
	}



	@Override
	public <R extends ClosedLanguage<O, O, R, ?>> R convert(final ClosedLanguage.Factory<O, O, R, ?> factory) {
		return this.complement.convert(factory).complement();
	}



}
