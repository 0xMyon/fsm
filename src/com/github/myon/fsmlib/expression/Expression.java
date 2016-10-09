package com.github.myon.fsmlib.expression;

import com.github.myon.fsmlib.container.Sequence;
import com.github.myon.fsmlib.immutable.ClosedLanguage;

/**
 * @author 0xMyon
 *
 * immutable based on underlying language
 * @param <O> based object type
 * @param <T> underlying type
 */
public abstract class Expression<O, T extends ClosedLanguage<O, T>> implements ClosedLanguage<O, Expression<O,T>> {

	public abstract T evaluate();

	@Override
	public Expression<O,T> complement() {
		return ComplementExpression.create(this);
	}

	@Override
	public Expression<O,T> union(final Expression<O,T> that) {
		return UnionExpression.create(this, that);
	}

	@Override
	public Expression<O,T> concat(final Expression<O,T> that) {
		return SequenceExpression.create(this,that);
	}

	@Override
	public Expression<O,T> iteration() {
		return IterationExpression.create(this);
	}

	@Override
	public boolean isEmpty() {
		return this.evaluate().isEmpty();
	}

	@Override
	public boolean isFinite() {
		return this.evaluate().isFinite();
	}

	@Override
	public boolean containsAll(final Expression<O,T> that) {
		return this.evaluate().containsAll(that.evaluate());
	}

	@Override
	public boolean contains(final Sequence<O> object) {
		return this.evaluate().contains(object);
	}

	@Override
	public boolean isEpsilon() {
		return this.evaluate().isEpsilon();
	}



	@Override
	public Expression<O,T> option() {
		// TODO Auto-generated method stub
		return null;
	}

}
