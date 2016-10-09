package com.github.myon.fsmlib.expression;

import com.github.myon.fsmlib.immutable.ClosedLanguage;

/**
 * @author 0xMyon
 *
 * complement expression
 * @param <O> based object type
 * @param <T> underlying type
 */
public class ComplementExpression<O, T extends ClosedLanguage<O, T>> extends Expression<O,T> {

	private final Expression<O,T> complement;

	private ComplementExpression(final Expression<O,T> complement) {
		this.complement = complement;
	}

	public static <O, T extends ClosedLanguage<O, T>> Expression<O,T> create(final Expression<O,T> complement) {
		if (complement instanceof ComplementExpression) {
			return ((ComplementExpression<O,T>) complement).complement;
		} else {
			return new ComplementExpression<>(complement);
		}
	}

	@Override
	public T evaluate() {
		return this.complement.evaluate().complement();
	}

}
