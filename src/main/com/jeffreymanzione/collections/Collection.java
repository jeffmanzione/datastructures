package com.jeffreymanzione.collections;

public interface Collection<E> {
	public abstract boolean delete(E element);

	public int size();

	public boolean isEmpty();

	public boolean contains(E element);
	
	public boolean insert(E element);
}
