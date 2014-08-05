package com.jeffreymanzione.maps.heaps;

import com.jeffreymanzione.collections.Container;

public interface Heap<P extends Comparable<P>, E> extends Container<E> {

	public abstract E delete();
	
	public abstract class Entry<K, V> {
		K key;
		V value;

		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

	}

	public void insert(P key, E value);

	public void insert(Entry<P, E> entry);

	public void union(Heap<P, E> heap);

	public Entry<P, E> deleteEntry();

	public boolean contains(E element);
	
	boolean delete(E value);
}
