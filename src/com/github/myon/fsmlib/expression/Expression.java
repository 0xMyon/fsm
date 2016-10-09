package com.github.myon.fsmlib.expression;

import com.github.myon.fsmlib.container.Sequence;
import com.github.myon.fsmlib.factory.LanguageFactory;
import com.github.myon.fsmlib.immutable.ClosedLanguage;

/**
 * @author 0xMyon
 *
 * immutable based on underlying language
 * @param <O> based object type
 * @param <T> underlying type
 */
public abstract class Expression<O, T extends ClosedLanguage<O, O, T>> implements ClosedLanguage<O, O, Expression<O,T>> {

	private final LanguageFactory<O, O, T> factory;

	public Expression(final LanguageFactory<O, O, T> factory) {
		this.factory = factory;
	}

	public T evaluate() {
		return this.convert(this.factory);
	}

	@Override
	public Expression<O,T> complement() {
		return ComplementExpression.create(this.factory, this);
	}

	@Override
	public Expression<O,T> union(final Expression<O,T> that) {
		return UnionExpression.create(this.factory, this, that);
	}

	@Override
	public Expression<O,T> concat(final Expression<O,T> that) {
		return SequenceExpression.create(this.factory, this,that);
	}

	@Override
	public Expression<O,T> iteration() {
		return IterationExpression.create(this.factory, this);
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


	@Override
	public LanguageFactory<O, O, Expression<O, T>> factory() {
		return new LanguageFactory<O, O, Expression<O,T>>() {
			@Override
			@SuppressWarnings("unchecked")
			public Expression<O, T> sequence( final O... objects) {
				return new SequenceExpression<>(Expression.this.factory, objects);
			}
			@Override
			@SuppressWarnings("unchecked")
			public Expression<O, T> intersection(final O... objects) {
				// TODO Auto-generated method stub
				return null;
			}
			@Override
			@SuppressWarnings("unchecked")
			public Expression<O, T> union(final O... objects) {
				return new UnionExpression<>(Expression.this.factory, objects);
			}
			@Override
			public Expression<O, T> element(final O object) {
				return new ElementaryExpression<O, T>(Expression.this.factory, object);
			}
		};
	}



}
