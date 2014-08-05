package com.jeffreymanzione.collections.queues.lists;

import com.jeffreymanzione.collections.Collection;

public interface List<E> extends Collection<E> {

	public boolean delete(E element);

	public E delete(int index);

	public E get(int index);

}
