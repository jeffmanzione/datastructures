package com.jeffreymanzione.collections.sets;

import com.jeffreymanzione.collections.AbstractCollection;

public abstract class AbstractSet<E> extends AbstractCollection<E> implements Set<E> {

	protected abstract class Node {
		private E element;
		private Node left, right;

		public Node(E element) {
			this.element = element;
		}

//		public abstract boolean insert(E element);
//		public abstract boolean delete(E element);
		
		public E getElement() {
			return element;
		}

		public void setElement(E element) {
			this.element = element;
		}

		public Node getLeft() {
			return left;
		}

		public void setLeft(Node left) {
			this.left = left;
		}

		public Node getRight() {
			return right;
		}

		public void setRight(Node right) {
			this.right = right;
		}

		public boolean hasLeft() {
			return left != null;
		}

		public boolean hasRight() {
			return right != null;
		}
		
		public Node getMaxChild() {
			if (this.hasRight()) {
				return right.getMaxChild();
			} else {
				return this;
			}
		}
		
		public Node getMinChild() {
			if (this.hasLeft()) {
				return left.getMinChild();
			} else {
				return this;
			}
		}
	}
}
