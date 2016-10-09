package com.github.myon.fsmlib.container;

import java.util.Objects;

import com.github.myon.fsmlib.mutable.MutableSymetricSet;
import com.github.myon.util.Anything;

public class SymetricSet<O> extends Anything implements MutableSymetricSet<O, SymetricSet<O>> {


	private boolean inverted = false;
	private final FiniteSet<O> data = new FiniteSet<>();


	@Override
	public void unite(final SymetricSet<O> that) {
		if (this.inverted && that.inverted) {
			this.data.intersect(that.data);
		} else if (this.inverted && !that.inverted) {
			this.data.subtract(that.data);
		} else if (!this.inverted && that.inverted) {
			this.data.assign(that.data.minus(this.data));
		} else if (!this.inverted && !that.inverted) {
			this.data.unite(that.data);
		}
	}

	@Override
	public void remove(final O object) {
		if (this.inverted) {
			this.data.add(object);
		} else {
			this.data.remove(object);
		}
	}

	@Override
	public void add(final O object) {
		if (this.inverted) {
			this.data.remove(object);
		} else {
			this.data.add(object);
		}
	}


	@Override
	public SymetricSet<O> copy() {
		final SymetricSet<O> result = new SymetricSet<>();
		result.unite(this);
		return result;
	}

	@Override
	public boolean isEmpty() {
		return this.isFinite() && this.data.isEmpty();
	}

	@Override
	public boolean isFinite() {
		return !this.inverted;
	}

	@Override
	public boolean containsAll(final SymetricSet<O> that) {
		if (this.inverted && that.inverted) {
			return that.data.containsAll(this.data);
		} else if (this.inverted && !that.inverted) {
			return this.isDisjunced(that);
		} else if (!this.inverted && that.inverted) {
			return false;
		} else /*if (!this.inverted && !that.inverted)*/ {
			return this.data.containsAll(that.data);
		}
	}

	@Override
	public boolean contains(final O object) {
		return this.inverted != this.data.contains(object);
	}

	@Override
	public void invert() {
		this.inverted = !this.inverted;
	}


	@Override
	public void clear() {
		this.inverted = false;
		this.data.clear();
	}

	@Override
	public String toString() {
		return (this.inverted?"!":"")+this.data.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.data, this.inverted);
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof SymetricSet) {
			final SymetricSet<?> that = (SymetricSet<?>) other;
			return this.data.equals(that.data) && this.inverted == that.inverted;
		}
		return false;
	}


}
