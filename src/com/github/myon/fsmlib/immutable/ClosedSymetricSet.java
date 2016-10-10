package com.github.myon.fsmlib.immutable;

/**
 * @author 0xMyon
 *
 * closed set, that may be inverted
 * @param <O> super type
 * @param <T> underling type
 */
public interface ClosedSymetricSet<O, B, T extends ClosedSymetricSet<O, B, T>> extends ClosedSet<O, B, T> {

	/**
	 * inverts the type
	 * @return a type, that contains all objects that are not contained in this type
	 */
	T complement();

	@Override
	default T intersection(final T that) {
		return this.complement().union(that.complement()).complement();
	}

	@Override
	default T minus(final T that) {
		return this.complement().union(that).complement();
	}

	@Override
	public Factory<O, B, T> factory();

	@Override
	public default <R extends ClosedSet<O, B, R>> R convert(final ClosedSet.Factory<O, B, R> factory) {
		throw new Error();
	}

	public <R extends ClosedSymetricSet<O, B, R>> R convert(final ClosedSymetricSet.Factory<O, B, R> factory);

	public static interface Factory<O, B, T extends ClosedSymetricSet<O, B, T>> extends ClosedSet.Factory<O, B, T> {

		@SuppressWarnings("unchecked")
		public T intersection(final B... objects);



	}

}
