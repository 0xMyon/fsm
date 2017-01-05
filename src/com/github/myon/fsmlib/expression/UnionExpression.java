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
public class UnionExpression<O, T extends ClosedLanguage<O, O, T, ?>> extends Expression<O, T> {

	@SafeVarargs
	private UnionExpression(final ClosedLanguage.Factory<O, O, T, ?> factory, final O... objects) {
		super(factory);
		for(final O object : objects) {
			this.summands.add(new ElementaryExpression<>(factory, object));
		}
	}


	private final FiniteSet<Expression<O,T>> summands = new FiniteSet<>();

	@SafeVarargs
	public static <O,T extends ClosedLanguage<O, O, T, ?>> Expression<O,T> create(final ClosedLanguage.Factory<O, O, T, ?> factory, final Expression<O,T>... summands) {
		if (summands.length == 1) {
			return summands[0];
		}
		final UnionExpression<O,T> result = new UnionExpression<>(factory);
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
	@SuppressWarnings("unchecked")
	public <R extends ClosedLanguage<O, O, R, ?>> R convert(final ClosedLanguage.Factory<O, O, R, ?> factory) {
		return this.summands.aggregate(()->factory.union(), (summand, result)-> {
			return summand.convert(factory).union(result);
		});
	}

}
