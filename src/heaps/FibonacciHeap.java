package heaps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Stack;

public class FibonacciHeap<P extends Comparable<P>, E> {

	private Node min;

	Set<Node> roots;

	Map<E, Node> all;

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
		Node n = new Node(priority, element);
		roots.add(n);
		all.put(element, n);
		if (min == null || min.priority.compareTo(n.priority) > 0) {
			min = n;
		}
	}

	// O(logn) delete min
	public E delete() {
		if (min != null) {
			E val = min.element;
			roots.addAll(min.children);
			roots.remove(min);
			all.remove(min.element);

			if (!roots.isEmpty()) {

				min = roots.stream().min((n1, n2) -> n1.priority.compareTo(n2.priority)).get();

				Object[] lvls = new Object[(int) (Math.log(all.size()) / Math.log(2.0) + 1.0)];
				Stack<Node> toInsert = new Stack<>();
				toInsert.addAll(roots);

				while (!toInsert.isEmpty()) {
					Node curr = toInsert.pop();

					if (lvls[curr.rank] == null) {
						lvls[curr.rank] = curr;
					} else {
						@SuppressWarnings("unchecked")
						Node other = (Node) lvls[curr.rank];
						lvls[curr.rank] = null;

						Node smaller, larger;

						if (curr.priority.compareTo(other.priority) < 0 ) {
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
		Node node = all.get(element);
		if (node != null) {
			node.priority = newPriority;
			if (node.parent == null || node.parent.priority.compareTo(newPriority) < 0) {
				if (newPriority.compareTo(min.priority) < 0) {
					min = node;
				}
			} else {

				Stack<Node> stack = new Stack<>();
				stack.push(node);

				while (!stack.isEmpty()) {
					Node curr = stack.pop();
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

	private class Node {
		P priority;
		E element;

		boolean marked;
		int rank;
		Set<Node> children;
		private Node parent;

		public Node(P priority, E element) {
			this.priority = priority;
			this.element = element;
			children = new HashSet<Node>();
		}

		public void add(Node larger) {
			larger.parent = Node.this;
			children.add(larger);
		}

		public void mark() {
			marked = true;
		}

		public void unmark() {
			marked = false;
		}
	}

	public static void main(String[] args) {
		FibonacciHeap<Integer, Integer> fq = new FibonacciHeap<>();
		
		List<Integer> sorted = new ArrayList<>();
		
		for (int i = 0; i < 100; i++) {
			sorted.add(i);
		}
		
		List<Integer> unsorted = new ArrayList<>(sorted);
		Collections.shuffle(unsorted);

		unsorted.forEach(e -> fq.insert(e, e));
		
		for (int i = 50; i < 100; i++) {
			fq.decreasePriority(i-100, i);
		}
		
		while(fq.size() > 0) {
			System.out.println(fq.delete());
		}
	}

}
