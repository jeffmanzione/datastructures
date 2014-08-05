package com.jeffreymanzione.collections;

import java.util.Iterator;

public interface Sorted<E> extends Iterable<E> {

	public abstract E first();

	public abstract E last();

	public abstract Iterator<E> forward();

	public abstract Iterator<E> backward();
}
