package com.github.myon.fsmlib.container;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.github.myon.fsmlib.factory.SetFactory;
import com.github.myon.fsmlib.immutable.ClosedSet;
import com.github.myon.fsmlib.mutable.MutableSet;
import com.github.myon.util.Anything;

public class FiniteSet<O> extends Anything implements MutableSet<O, O, FiniteSet<O>> {

	protected final Set<O> data = new HashSet<>();

	@SafeVarargs
	public FiniteSet(final O... objects) {
		for(final O object : objects) {
			this.data.add(object);
		}
	}

	@Override
	public boolean isEmpty() {
		return this.data.isEmpty();
	}

	@Override
	public boolean isFinite() {
		return true;
	}

	@Override
	public boolean containsAll(final FiniteSet<O> that) {
		return this.data.containsAll(that.data);
	}

	@Override
	public boolean contains(final O object) {
		return this.data.contains(object);
	}

	@Override
	public void subtract(final FiniteSet<O> that) {
		this.data.removeAll(that.data);
	}


	@Override
	public void unite(final FiniteSet<O> that) {
		this.data.addAll(that.data);
	}

	public void invert(final FiniteSet<O> universe) {
		final FiniteSet<O> x = this.copy();
		this.clear();
		this.unite(universe);
		this.subtract(x);
	}

	@Override
	public void intersect(final FiniteSet<O> that) {
		final Iterator<O> it = this.data.iterator();
		while(it.hasNext()) {
			if (!that.contains(it.next())) {
				it.remove();
			}
		}
	}

	@Override
	public void remove(final O object) {
		this.data.remove(object);
	}

	@Override
	public void add(final O object) {
		this.data.add(object);
	}

	@Override
	public FiniteSet<O> copy() {
		final FiniteSet<O> result = new FiniteSet<>();
		result.unite(this);
		return result;
	}

	public void assign(final FiniteSet<O> that) {
		this.clear();
		this.unite(that);
	}

	@Override
	public void clear() {
		this.data.clear();
	}



	@Override
	public String toString() {
		return this.data.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.data);
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof FiniteSet) {
			final FiniteSet<?> that = (FiniteSet<?>) other;
			return this.data.equals(that.data);
		}
		return false;
	}

	/**
	 * applies a function to all objects in the set
	 * @param consumer
	 */
	public void forAll(final Consumer<O> consumer) {
		for(final O object: this.data) {
			consumer.accept(object);
		}
	}

	public FiniteSet<O> findAll(final Predicate<O> predicate) {
		final FiniteSet<O> result = new FiniteSet<>();
		for(final O object: this.data) {
			if (predicate.test(object)) {
				result.add(object);
			}
		}
		return result;
	}

	public O findFirst(final Predicate<O> predicate) {
		for(final O object: this.data) {
			if (predicate.test(object)) {
				return object;
			}
		}
		return null;
	}

	public <R> FiniteSet<R> map(final Function<O,R> function) {
		final FiniteSet<R> result = new FiniteSet<>();
		for(final O object: this.data) {
			result.add(function.apply(object));
		}
		return result;
	}

	public <R> R aggregate(final Supplier<R> neutral, final BiFunction<O, R, R> aggregator) {
		R result = neutral.get();
		for(final O object:this.data) {
			result = aggregator.apply(object, result);
		}
		return result;
	}


	@Override
	public SetFactory<O, O, FiniteSet<O>> factory() {
		return new SetFactory<O, O, FiniteSet<O>>() {
			@Override
			@SuppressWarnings("unchecked")
			public FiniteSet<O> union(final O... objects) {
				return new FiniteSet<>(objects);
			}
			@Override
			public FiniteSet<O> element(final O object) {
				return new FiniteSet<>(object);
			}
			@Override
			public FiniteSet<O> empty() {
				return new FiniteSet<>();
			}
		};
	}


	@Override
	@SuppressWarnings("unchecked")
	public <R extends ClosedSet<O, O, R>> R convert(final SetFactory<O, O, R> factory)  {
		return factory.union( (O[]) this.data.toArray()  );
	}









}
