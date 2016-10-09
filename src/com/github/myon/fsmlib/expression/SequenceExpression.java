package com.github.myon.fsmlib.expression;

import com.github.myon.fsmlib.container.Sequence;
import com.github.myon.fsmlib.immutable.ClosedLanguage;

/**
 * @author 0xMyon
 *
 * sequence expression
 * @param <O> based object type
 * @param <T> underlying type
 */
public class SequenceExpression<O, T extends ClosedLanguage<O, T>> extends Expression<O,T> {

	private final Sequence<Expression<O,T>> factors = new Sequence<>();

	@SafeVarargs
	public static <O, T extends ClosedLanguage<O, T>> SequenceExpression<O,T> create(final Expression<O,T>... factors) {
		final SequenceExpression<O,T> result = new SequenceExpression<>();
		for(final Expression<O,T> factor : factors) {
			if (factor instanceof SequenceExpression) {
				result.factors.append(((SequenceExpression<O,T>)factor).factors);
			} else {
				result.factors.append(factor);
			}
		}
		return result;
	}


	@Override
	public T evaluate() {
		// TODO Auto-generated method stub
		return null;
	}

}
