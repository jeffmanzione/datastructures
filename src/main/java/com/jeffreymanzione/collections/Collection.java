package com.jeffreymanzione.collections;

public interface Collection<E> extends Container<E>, Iterable<E> {

	public boolean delete(E element);

	public boolean contains(E element);

	public boolean insert(E element);

	public boolean containsAll(Collection<E> collection);

}
