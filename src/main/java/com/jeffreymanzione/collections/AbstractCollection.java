package com.jeffreymanzione.collections;

public abstract class AbstractCollection<E> implements Collection<E> {
	@Override
	public boolean containsAll(Collection<E> collection) {
		for (E elt : collection) {
			if (!this.contains(elt)) {
				return false;
			}
		}

		return true;
	}
}
