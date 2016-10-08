package com.github.myon.fsmlib.container;

import java.util.LinkedList;
import java.util.List;

import com.github.myon.fsmlib.mutable.MutableSequence;

public class Sequence<O> implements MutableSequence<O, Sequence<O>> {

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

}
