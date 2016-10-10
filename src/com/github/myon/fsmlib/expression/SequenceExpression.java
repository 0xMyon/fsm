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
public class SequenceExpression<O, T extends ClosedLanguage<O, O, T, ?>> extends Expression<O,T> {

	// TODO return elementary on single element

	@SafeVarargs
	public SequenceExpression(final ClosedLanguage.Factory<O, O, T, ?> factory, final O... objects) {
		super(factory);
		for(final O object : objects) {
			this.factors.append(new ElementaryExpression<>(factory, object));
		}
	}



	private final Sequence<Expression<O,T>> factors = new Sequence<>();



	@SafeVarargs
	public static <O, T extends ClosedLanguage<O, O, T, ?>> SequenceExpression<O,T> create(final ClosedLanguage.Factory<O, O, T, ?> factory, final Expression<O,T>... factors) {
		final SequenceExpression<O,T> result = new SequenceExpression<>(factory);
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
	@SuppressWarnings("unchecked")
	public <R extends ClosedLanguage<O, O, R, ?>> R convert(final ClosedLanguage.Factory<O, O, R, ?> factory) {
		return this.factors.aggregate(()->factory.sequence(), (factor, result)-> {
			return factor.convert(factory).concat(result);
		});
	}





}
