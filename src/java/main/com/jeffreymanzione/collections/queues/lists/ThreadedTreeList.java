package com.jeffreymanzione.collections.queues.lists;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.jeffreymanzione.collections.AbstractCollection;

public class ThreadedTreeList<E extends Comparable<E>> extends
		AbstractCollection<E> implements SortedList<E> {
	/*
	 * Fields and such
	 */
	private Node head = null;

	private Node start = null, end = null;

	private int size = 0;

	/* Inner Node class that will hold data */
	private class Node {
		E element;

		Node parent = null, left = null, right = null;

		boolean leftTag = true, rightTag = true;

		public Node(E element) {
			this.element = element;
		}

		public boolean hasLeftChild() {
			return left != null && !leftTag;
		}

		public boolean hasRightChild() {
			return right != null && !rightTag;
		}

		public Node oneGreater() {
			Node curr = right;
			while (true) {
				if (curr == null) {
					return null;
				} else if (curr.hasLeftChild()) {
					curr = curr.left;
				} else {
					return curr;
				}
			}
		}

		public Node oneLess() {
			Node curr = left;
			while (true) {
				if (curr == null) {
					return null;
				} else if (curr.hasRightChild()) {
					curr = curr.right;
				} else {
					return curr;
				}
			}
		}

		public String toString() {
			return element.toString();
		}

		public void delete() {
			// System.out.println("Deleting: " + this + "; Left: " + (leftTag ?
			// "F" : "") + left + "; Right: " + right + (rightTag ? "F" : ""));

			if (!hasLeftChild() && !hasRightChild()) {

				if (this == head) {
					head = null;
					start = null;
					end = null;
				} else {
					parent.dropChild(this);
				}

			} else if (hasLeftChild() && hasRightChild()) {
				// System.out.println("Deleting guy with two children.");

				Node oneGreater = oneGreater();
				E oneGreaterData = oneGreater().element;
				oneGreater.delete();
				element = oneGreaterData;

			} else if (hasLeftChild()) {
				replaceWithLeftChild();
			} else {
				replaceWithRightChild();
			}

		}

		private void replaceWithLeftChild() {
			// System.out.println("Deleting guy who only has left child.");
			Node replacement = left;

			Node max = replacement.max();

			// System.out.println("Max found: " + max.data);

			max.right = right;
			max.rightTag = rightTag;

			if (this == end) {
				// System.out.println("He was an end node.");
				end = max;
			}

			if (this == head) {
				// System.out.println("Deleting head.");
				head = replacement;

			} else if (parent.left == this) {
				// System.out.println("Was from left.");
				parent.left = replacement;
			} else {
				// System.out.println("Was from right.");
				parent.right = replacement;
			}

			replacement.parent = parent;
		}

		private void replaceWithRightChild() {
			// System.out.println("Deleting guy who only has right child.");
			Node replacement = right;

			Node min = replacement.min();

			// System.out.println("Min found: " + min.data);

			min.left = left;
			min.leftTag = leftTag;

			if (this == start) {
				// System.out.println("He was a start node.");
				start = min;
			}

			if (this == head) {
				// System.out.println("Deleting head.");
				head = replacement;

			} else if (parent.left == this) {
				// System.out.println("Was from left.");
				parent.left = replacement;
			} else {
				// System.out.println("Was from right.");
				parent.right = replacement;
			}

			replacement.parent = parent;
		}

		private Node min() {
			if (leftTag) {
				return this;
			} else {
				return left.min();
			}
		}

		private Node max() {
			if (rightTag) {
				return this;
			} else {
				return right.max();
			}
		}

		private void dropChild(Node child) {
			if (left == child) {
				// System.out.println("Deleting guy on left with no child.");
				leftTag = true;
				left = child.left;

				if (child == start) {
					start = child.right;
				}

			} else if (right == child) {
				// System.out.println("Deleting guy on right with no child.");
				rightTag = true;
				right = child.right;

				if (child == end) {
					end = child.left;
				}
			} else {
				// System.out.println("WHAT???1");
			}
		}

		// public int height() {
		// int leftHeight = 0, rightHeight = 0;
		//
		// if (left != null && !leftTag) {
		// leftHeight = left.height();
		// }
		//
		// if (right != null && !rightTag) {
		// rightHeight = right.height();
		// }
		//
		// return ((leftHeight > rightHeight) ? leftHeight : rightHeight) + 1;
		// }

	}

	/* Insert a new value into the tree! Yay! */
	public boolean insert(E element) {
		// Create the new node
		Node newb = new Node(element);

		// Base case
		if (head == null) {
			head = start = end = newb;
			// height = 1;
			// size = 1;
		} /* Not the base case */else {
			Node curr = head;

			// Are we done inserting?
			boolean done = false;
			// Special case checking for smallest and largest node insertion
			boolean onlyLeft = true;
			boolean onlyRight = true;

			// int height = 2;

			while (!done) {
				// Determine if we are to traverse left or right
				boolean isLeft = element.compareTo(curr.element) < 0;

				// For the special cases mentioned above
				if (isLeft)
					onlyRight = false;
				else
					onlyLeft = false;

				// Which node is the next
				Node next = (isLeft ? curr.left : curr.right);

				// Go to next node if it is not null and not across a thread
				if (next != null && !(isLeft ? curr.leftTag : curr.rightTag)) {
					curr = next;
					// height++;
				} /* Otherwise */else {

					// if we are to insert to the left child
					if (isLeft) {
						Node oldLeft = curr.left;

						curr.left = newb;
						curr.leftTag = false;

						// if is smallest node
						if (onlyLeft) {
							newb.left = null;
							// Set as the start of the traversal
							start = newb;
						} /* otherwise */else {
							newb.left = oldLeft;
						}

						// set the right child of the new node
						newb.right = curr;

					} else /* to the right child */{
						Node oldRight = curr.right;

						curr.right = newb;
						curr.rightTag = false;

						// if is the biggest node
						if (onlyRight) {
							newb.right = null;
							// Set as the end of the traversal
							end = newb;
						} /* otherwise */else {
							newb.right = oldRight;
						}

						// set the left child of the new node
						newb.left = curr;
					}

					// Set the parent
					newb.parent = curr;

					// We are done with the loop
					done = true;

					size++;
				}

			}

		}
		return true;
	}

	public boolean contains(E element) {

		// Base case
		if (head == null) {
			return false;
		} /* Not the base case */else {
			Node curr = head;

			while (true) {
				// Determine if we are to traverse left or right
				int val = element.compareTo(curr.element);

				if (val == 0) {
					return true;
				}

				boolean isLeft = val < 0;

				// Which node is the next
				Node next = (isLeft ? curr.left : curr.right);

				// Go to next node if it is not null and not across a thread
				if (next != null && !(isLeft ? curr.leftTag : curr.rightTag)) {
					curr = next;

				} else /* Otherwise */{
					return false;
				}

			}

		}
	}

	// public int height() {
	// if (head == null) {
	// return 0;
	// } else {
	// return head.height();
	// }
	// }

	@Override
	public int size() {
		if (head == null) {
			return 0;
		} else {
			return size;
		}
	}

	@Override
	public boolean isEmpty() {
		return head == null;
	}

	@Override
	public boolean delete(E element) {
		// Base case
		if (head != null) {
			Node curr = head;

			while (curr != null) {
				// Determine if we are to traverse left or right
				int val = element.compareTo(curr.element);

				if (val == 0) {
					curr.delete();
					size--;
					return true;
				} else {

					boolean isLeft = val < 0;

					// Which node is the next
					Node next = (isLeft ? curr.left : curr.right);

					// Go to next node if it is not null and not across a thread
					if (next != null
							&& !(isLeft ? curr.leftTag : curr.rightTag)) {
						curr = next;

					} else {
						break;
					}
				}
			}
			return false;
		} else {
			return false;
		}
	}

	@Override
	public E delete(int index) {
		Iterator<E> iterator = this.iterator();

		if (index >= size) {
			throw new NoSuchElementException();
		}

		E element = null;

		for (int i = 0; i < index; i++) {
			element = iterator.next();
		}

		this.delete(element);

		return element;
	}

	@Override
	public E get(int index) {
		Iterator<E> iterator = this.iterator();

		if (index >= size) {
			throw new NoSuchElementException();
		}

		E element = null;

		for (int i = 0; i < index; i++) {
			element = iterator.next();
		}

		return element;
	}

	@Override
	public void clear() {
		head = start = end = null;
		size = 0;
	}

	@Override
	public Iterator<E> iterator() {

		return new Iterator<E>() {

			Node curr = start, prev;

			@Override
			public boolean hasNext() {
				return curr != end;
			}

			@Override
			public E next() {
				prev = curr;
				E element = curr.element;

				// if we have a right tag, we move to the right
				if (curr.rightTag) {
					curr = curr.right;
				} else /* Otherwise, we find the next largest elt */{
					curr = curr.oneGreater();
				}

				return element;
			}

			@Override
			public void remove() {
				ThreadedTreeList.this.delete(curr.element);
				if (prev == null) {
					curr = head;
				} else {
					curr = prev;
				}
				size--;
			}

		};
	}

	public Iterator<E> forward() {
		return this.iterator();
	}

	public Iterator<E> backward() {
		return new Iterator<E>() {

			Node curr = end, prev;

			@Override
			public boolean hasNext() {
				return curr != head;
			}

			@Override
			public E next() {
				prev = curr;

				E element = curr.element;

				// if we have a left tag, we move to the left
				if (curr.leftTag) {
					curr = curr.left;
				} else /* Otherwise, we find the next largest elt */{
					curr = curr.oneLess();
				}

				return element;
			}

			@Override
			public void remove() {
				ThreadedTreeList.this.delete(curr.element);
				if (prev == null) {
					curr = end;
				} else {
					curr = prev;
				}
				size--;
			}
		};
	}

	@Override
	public E first() {
		return start.element;
	}

	@Override
	public E last() {
		return end.element;
	}
}
