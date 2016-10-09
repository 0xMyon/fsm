package com.github.myon.fsmlib.expression;

import com.github.myon.fsmlib.container.FiniteSet;
import com.github.myon.fsmlib.immutable.ClosedLanguage;

/**
 * @author 0xMyon
 *
 * union expression
 * @param <O> based object type
 * @param <T> underlying type
 */
public class UnionExpression<O,T extends ClosedLanguage<O, T>> extends Expression<O,T> {

	private final FiniteSet<Expression<O,T>> summands = new FiniteSet<>();

	@SafeVarargs
	public static <O,T extends ClosedLanguage<O, T>> UnionExpression<O,T> create(final Expression<O,T>... summands) {
		final UnionExpression<O,T> result = new UnionExpression<>();
		for(final Expression<O,T> summand : summands) {
			if (summand instanceof UnionExpression) {
				result.summands.unite(((UnionExpression<O,T>)summand).summands);
			} else {
				result.summands.add(summand);
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
