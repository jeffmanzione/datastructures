package com.jeffreymanzione.collections.stacks;

import com.jeffreymanzione.collections.Container;

public interface Stack<E> extends Container<E> {
	public void push(E element);

	public E pop();

	public E peek();

}
