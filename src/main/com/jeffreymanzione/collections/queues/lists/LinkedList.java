package com.jeffreymanzione.collections.queues.lists;

import java.util.Iterator;


public interface LinkedList<E> extends List<E> {
	public Iterator<E> forward();
	public Iterator<E> backward();
}
