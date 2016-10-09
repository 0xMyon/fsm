package com.github.myon.fsmlib.container;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.github.myon.fsmlib.mutable.MutableSequence;
import com.github.myon.util.Anything;

public class Sequence<O> extends Anything implements MutableSequence<O, Sequence<O>> {

	private final List<O> data = new LinkedList<>();

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

}
