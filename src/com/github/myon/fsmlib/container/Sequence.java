package com.github.myon.fsmlib.container;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.github.myon.fsmlib.immutable.ClosedSequence;
import com.github.myon.fsmlib.mutable.MutableSequence;
import com.github.myon.util.Anything;

public class Sequence<O> extends Anything implements MutableSequence<O, O, Sequence<O>, Sequence.Factory<O>> {

	protected final List<O> data = new LinkedList<>();

	@SafeVarargs
	public Sequence(final O... objects) {
		for(final O object: objects) {
			this.data.add(object);
		}
	}

	@Override
	public boolean isFinite() {
		return true;
	}

	@Override
	public boolean isEpsilon() {
		return this.data.isEmpty();
	}

	@Override
	public void append(final Sequence<O> that) {
		this.data.addAll(that.data);
	}

	@Override
	public void prepend(final Sequence<O> that) {
		this.data.addAll(0, that.data);
	}

	@Override
	public void append(final O object) {
		this.data.add(object);
	}

	@Override
	public void prepend(final O object) {
		this.data.add(0, object);
	}

	@Override
	public Sequence<O> copy() {
		final Sequence<O> result = new Sequence<>();
		result.append(this);
		return result;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.data);
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof Sequence) {
			final Sequence<?> that = (Sequence<?>) other;
			return this.data.equals(that.data);
		}
		return false;
	}

	@Override
	public String toString() {
		return this.data.toString();
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

	public Sequence<O> findAll(final Predicate<O> predicate) {
		final Sequence<O> result = new Sequence<>();
		for(final O object: this.data) {
			if (predicate.test(object)) {
				result.append(object);
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

	public <R> Sequence<R> map(final Function<O,R> function) {
		final Sequence<R> result = new Sequence<>();
		for(final O object: this.data) {
			result.append(function.apply(object));
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
	public Factory<O> factory() {
		return new Factory<>();
	}


	@Override
	@SuppressWarnings("unchecked")
	public <R extends ClosedSequence<O, O, R, ?>> R convert(final ClosedSequence.Factory<O, O, R, ?> factory) {
		return factory.sequence( (O[]) this.data.toArray()  );
	}

	public static final class Factory<O> implements MutableSequence.Factory<O, O, Sequence<O>, Factory<O>> {
		@Override
		@SuppressWarnings("unchecked")
		public Sequence<O> sequence(final O... objects) {
			return new Sequence<>(objects);
		}
		@Override
		public Sequence<O> element(final O object) {
			return new Sequence<>(object);
		}
		@Override
		public Sequence<O> epsilon() {
			return new Sequence<>();
		}
	}

}
