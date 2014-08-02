package com.jeffreymanzione.maps.heaps;

public interface Heap<P extends Comparable<P>, E> {

	public abstract E delete();
	
	public abstract class Entry<K, V> {
		K priority;
		V element;

		public Entry(K priority, V element) {
			this.priority = priority;
			this.element = element;
		}

		public K getKey() {
			return priority;
		}

		public V getValue() {
			return element;
		}

	}

	public void insert(P priority, E element);

	public void insert(Entry<P, E> entry);

	public void union(Heap<P, E> heap);

	public Entry<P, E> deleteEntry();

	public boolean isEmpty();
	
	public int size();

	boolean delete(E element);

	boolean contains(E element);
}
