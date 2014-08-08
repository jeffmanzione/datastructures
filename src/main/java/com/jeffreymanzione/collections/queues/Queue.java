package com.jeffreymanzione.collections.queues;

import com.jeffreymanzione.collections.Container;

public interface Queue<E> extends Container<E> {

	public void enqueue(E element);
	
	public E peek();
	
	public E dequeue();
}
