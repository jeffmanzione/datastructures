package com.jeffreymanzione.collections.sets;

import java.util.Iterator;

import com.jeffreymanzione.collections.Collection;
import com.jeffreymanzione.collections.queues.Queue;
import com.jeffreymanzione.collections.queues.StrictQueue;
import com.jeffreymanzione.collections.stacks.Stack;
import com.jeffreymanzione.collections.stacks.StrictStack;

public class NaturalTreeSet<E extends Comparable<E>> extends AbstractSet<E>
		implements SortedSet<E> {

	private TreeNode head;

	private int size;

	private class TreeNode extends Node {

		public TreeNode(E element) {
			super(element);
		}

		public boolean insert(E element) {
			if (element.compareTo(this.getElement()) > 0) {
				if (this.hasRight()) {
					return this.getRight().insert(element);
				} else {
					this.setRight(new TreeNode(element));
					size++;
					return true;
				}
			} else if (element.compareTo(this.getElement()) < 0) {
				if (this.hasLeft()) {
					return this.getLeft().insert(element);
				} else {
					this.setLeft(new TreeNode(element));
					size++;
					return true;
				}
			} else {
				return false;
			}

		}

		@SuppressWarnings("unchecked")
		@Override
		public TreeNode getLeft() {
			return (TreeNode) super.getLeft();
		}

		@SuppressWarnings("unchecked")
		@Override
		public TreeNode getRight() {
			return (TreeNode) super.getRight();
		}

		public String toString() {

			if (!this.hasLeft() && !this.hasRight()) {
				return this.getElement().toString();
			} else {
				return "(" + this.getElement().toString() + ":"
						+ (this.hasLeft() ? this.getLeft().toString() : null)
						+ ","
						+ (this.hasRight() ? this.getRight().toString() : null)
						+ ")";
			}
		}

	}

	@Override
	public boolean delete(E element) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean contains(E element) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insert(E element) {
		if (head == null) {
			head = new TreeNode(element);
			size++;
			return true;
		} else {
			return head.insert(element);
		}
	}

	@Override
	public String toString() {

		if (head == null) {
			return "()";
		} else {
			return head.toString();
		}
	}

	@Override
	public void clear() {
		size = 0;
		head = null;
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {

			Queue<E> tempQueue = new StrictQueue<>();

			{
				buildInorder(head);
			}
			
			private void buildInorder(Node cur) {
				if (cur != null) {
					buildInorder(cur.getLeft());
					tempQueue.enqueue(cur.getElement());
					buildInorder(cur.getRight());
				}
			}

			@Override
			public boolean hasNext() {
				return !tempQueue.isEmpty();
			}

			@Override
			public E next() {
				return tempQueue.dequeue();
			}

		};

	}

	@Override
	public E first() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E last() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<E> forward() {
		return this.iterator();
	}

	@Override
	public Iterator<E> backward() {
		// TODO Auto-generated method stub
		return null;
	}

}
