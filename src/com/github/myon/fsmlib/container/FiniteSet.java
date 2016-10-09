package com.github.myon.fsmlib.container;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import com.github.myon.fsmlib.mutable.MutableSet;
import com.github.myon.util.Anything;

public class FiniteSet<O> extends Anything implements MutableSet<O, FiniteSet<O>> {

	private final Set<O> data = new HashSet<>();

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

}
