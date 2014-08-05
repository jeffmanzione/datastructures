package com.jeffreymanzione.collections.queues.lists;

public interface UnorderedList<E> extends List<E> {
	public void insertAt(int index, E element);
	public void set(int index, E element);
}
