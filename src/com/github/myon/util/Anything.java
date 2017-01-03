package com.github.myon.util;

public abstract class Anything {

	@Override
	public abstract String toString();

	@Override
	public abstract int hashCode();

	@Override
	public abstract boolean equals(Object other);

	public static boolean equals(final Object left, final Object right) {
		return (left == null && right == null) || left.equals(right);
	}


}
