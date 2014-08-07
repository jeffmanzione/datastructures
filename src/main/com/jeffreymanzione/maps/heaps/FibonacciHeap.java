package com.jeffreymanzione.maps.heaps;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Stack;

public class FibonacciHeap<P extends Comparable<P>, E> extends
		AbstractHeap<P, E> {

	private class FibonacciEntry<K, V> extends Entry<K, V> {

		private boolean marked;
		private int rank;
		private Set<FibonacciEntry<K, V>> children;
		private FibonacciEntry<K, V> parent;

		public FibonacciEntry(K priority, V element) {
			super(priority, element);
			children = new HashSet<>();
		}

		private void add(FibonacciEntry<K, V> larger) {
			larger.parent = FibonacciEntry.this;
			children.add(larger);
		}

		private void mark() {
			marked = true;
		}

		private void unmark() {
			marked = false;
		}

	}

	private FibonacciEntry<P, E> min;

	private Set<FibonacciEntry<P, E>> roots;

	private Map<E, FibonacciEntry<P, E>> all;

	public FibonacciHeap() {
		min = null;
		roots = new HashSet<>();
		all = new HashMap<>();
	}

	public int size() {
		return all.size();
	}

	// O(c) insertion
	public void insert(P priority, E element) {
		FibonacciEntry<P, E> n = new FibonacciEntry<>(priority, element);
		roots.add(n);
		all.put(element, n);
		if (min == null || min.key.compareTo(n.key) > 0) {
			min = n;
		}
	}

	// O(logn) delete min
	public E delete() {
		if (min != null) {
			E val = min.value;
			roots.addAll(min.children);
			roots.remove(min);
			all.remove(min.value);

			if (!roots.isEmpty()) {

				min = roots.stream().min((n1, n2) -> n1.key.compareTo(n2.key))
						.get();

				Object[] lvls = new Object[(int) (Math.log(all.size())
						/ Math.log(2.0) + 1.0)];
				Stack<FibonacciEntry<P, E>> toInsert = new Stack<>();
				toInsert.addAll(roots);

				while (!toInsert.isEmpty()) {
					FibonacciEntry<P, E> curr = toInsert.pop();

					if (lvls[curr.rank] == null) {
						lvls[curr.rank] = curr;
					} else {
						@SuppressWarnings("unchecked")
						FibonacciEntry<P, E> other = (FibonacciEntry<P, E>) lvls[curr.rank];
						lvls[curr.rank] = null;

						FibonacciEntry<P, E> smaller, larger;

						if (curr.key.compareTo(other.key) < 0) {
							smaller = curr;
							larger = other;
						} else {
							smaller = other;
							larger = curr;
						}

						smaller.add(larger);
						smaller.rank++;
						roots.remove(larger);
						toInsert.push(smaller);
					}
				}
			}

			return val;

		} else {
			return null;
		}
	}

	// O(c) amortized
	public void decreasePriority(P newPriority, E element) {
		FibonacciEntry<P, E> node = all.get(element);
		if (node != null) {
			node.key = newPriority;
			if (node.parent == null
					|| node.parent.key.compareTo(newPriority) < 0) {
				if (newPriority.compareTo(min.key) < 0) {
					min = node;
				}
			} else {

				Stack<FibonacciEntry<P, E>> stack = new Stack<>();
				stack.push(node);

				while (!stack.isEmpty()) {
					FibonacciEntry<P, E> curr = stack.pop();
					curr.parent.children.remove(curr);
					roots.add(curr);
					curr.unmark();

					if (curr.parent.marked) {
						stack.push(curr.parent);
					} else {
						curr.parent.mark();
					}

					curr.parent = null;
				}
			}
		} else {
			throw new NoSuchElementException();
		}
	}

	public boolean delete(E element) {

		if (all.containsKey(element)) {
			FibonacciEntry<P, E> toDelete = all.get(element);
			roots.addAll(toDelete.children);

			toDelete.children.forEach(n -> n.parent = null);

			if (roots.contains(element)) {
				roots.remove(element);
			}

			all.remove(element);

			if (toDelete.parent != null) {
				Stack<FibonacciEntry<P, E>> stack = new Stack<>();
				stack.push(toDelete.parent);
				while (!stack.isEmpty()) {
					FibonacciEntry<P, E> curr = stack.pop();

					if (curr.marked) {
						stack.push(curr.parent);
						curr.parent = null;
						roots.add(curr);
					} else {
						curr.mark();
					}
				}
			}

			return true;
		} else {
			return false;
		}
	}

	public Entry<P, E> deleteEntry(E element) {

		if (all.containsKey(element)) {
			FibonacciEntry<P, E> toDelete = all.get(element);
			roots.addAll(toDelete.children);

			toDelete.children.forEach(n -> n.parent = null);

			if (roots.contains(element)) {
				roots.remove(element);
			}

			Entry<P, E> wasRemoved = all.remove(element);

			if (toDelete.parent != null) {
				Stack<FibonacciEntry<P, E>> stack = new Stack<>();
				stack.push(toDelete.parent);
				while (!stack.isEmpty()) {
					FibonacciEntry<P, E> curr = stack.pop();

					if (curr.marked) {
						stack.push(curr.parent);
						curr.parent = null;
						roots.add(curr);
					} else {
						curr.mark();
					}
				}
			}

			return wasRemoved;
		} else {
			return null;
		}
	}

	@Override
	public void union(Heap<P, E> heap) {
		if (heap instanceof FibonacciHeap) {
			FibonacciHeap<P, E> fibHeap = (FibonacciHeap<P, E>) heap;
			this.roots.addAll(fibHeap.roots);
			this.all.putAll(fibHeap.all);
		} else {
			super.union(heap);
		}
	}

	@Override
	public void insert(Entry<P, E> entry) {
		this.insert(entry.getKey(), entry.getValue());
	}

	@Override
	public Entry<P, E> deleteEntry() {
		if (min != null) {
			roots.addAll(min.children);
			roots.remove(min);
			Entry<P, E> wasRemoved = all.remove(min.value);

			if (!roots.isEmpty()) {

				min = roots.stream().min((n1, n2) -> n1.key.compareTo(n2.key))
						.get();

				Object[] lvls = new Object[(int) (Math.log(all.size())
						/ Math.log(2.0) + 1.0)];
				Stack<FibonacciEntry<P, E>> toInsert = new Stack<>();
				toInsert.addAll(roots);

				while (!toInsert.isEmpty()) {
					FibonacciEntry<P, E> curr = toInsert.pop();

					if (lvls[curr.rank] == null) {
						lvls[curr.rank] = curr;
					} else {
						@SuppressWarnings("unchecked")
						FibonacciEntry<P, E> other = (FibonacciEntry<P, E>) lvls[curr.rank];
						lvls[curr.rank] = null;

						FibonacciEntry<P, E> smaller, larger;

						if (curr.key.compareTo(other.key) < 0) {
							smaller = curr;
							larger = other;
						} else {
							smaller = other;
							larger = curr;
						}

						smaller.add(larger);
						smaller.rank++;
						roots.remove(larger);
						toInsert.push(smaller);
					}
				}
			}

			return wasRemoved;

		} else {
			return null;
		}
	}

	@Override
	public boolean contains(E element) {
		return all.containsValue(element);
	}

	@Override
	public void clear() {
		min = null;
		roots.clear();
		all.clear();
	}

}
