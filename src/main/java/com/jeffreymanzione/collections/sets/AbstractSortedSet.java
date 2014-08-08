package com.jeffreymanzione.collections.sets;

public abstract class AbstractSortedSet<E> extends AbstractSet<E> implements
		SortedSet<E> {

	protected Node head;

	protected int size;

	enum Direction {
		Left, Right
	}

	protected abstract class Node {
		private E element;
		private Node left, right;

		public Node(E element) {
			this.element = element;
		}

		public String toString() {

			if (!this.hasLeft() && !this.hasRight()) {
				return this.getElement().toString();
			} else {
				return "(" + this.getElement().toString() + ":"
						+ (this.hasLeft() ? this.getLeft().toString() : "_")
						+ ","
						+ (this.hasRight() ? this.getRight().toString() : "_")
						+ ")";
			}
		}

		public abstract boolean delete(E element, Node parent, Direction dir);

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

		public class Bottom<T extends Node> {
			T parent, child;
			Direction dir;

			Bottom(T parent, T child, Direction dir) {
				this.parent = parent;
				this.child = child;
				this.dir = dir;
			}

		}

		public Bottom<? extends Node> getMaxChild() {
			if (this.hasRight()) {
				@SuppressWarnings("unchecked")
				Bottom<Node> result = (Bottom<Node>) right.getMaxChild();

				if (result.parent == null) {
					result.parent = this;
					result.dir = Direction.Right;
				}

				return result;
			} else {
				return new Bottom<Node>(null, this, null);
			}
		}

		public Bottom<? extends Node> getMinChild() {
			if (this.hasLeft()) {
				@SuppressWarnings("unchecked")
				Bottom<Node> result = (Bottom<Node>) left.getMinChild();
				if (result.parent == null) {
					result.parent = this;
					result.dir = Direction.Left;
				}

				return result;

			} else {
				return new Bottom<Node>(null, this, null);
			}
		}

		public abstract boolean contains(E element);

		public void set(Direction dir, Node node) {
			if (dir == Direction.Left) {
				this.setLeft(node);
			} else {
				this.setRight(node);
			}
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
	public boolean delete(E element) {
		if (head == null) {
			return false;
		} else {
			if (head.delete(element, null, null)) {
				size--;
				return true;
			} else {
				return false;
			}
		}
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
		if (head == null) {
			return false;
		} else {
			return head.contains(element);
		}
	}

	@Override
	public void clear() {
		size = 0;
		head = null;
	}

}
