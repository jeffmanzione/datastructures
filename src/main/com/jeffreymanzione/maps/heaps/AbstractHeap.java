package com.jeffreymanzione.maps.heaps;

public abstract class AbstractHeap<P extends Comparable<P>, E> implements
		Heap<P, E> {

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	public void union(Heap<P, E> heap) {
		while (!heap.isEmpty()) {
			heap.insert(heap.deleteEntry());
		}

	}

}
