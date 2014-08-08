package com.jeffreymanzione.collections.sets;

import java.util.Iterator;

import com.jeffreymanzione.collections.Collection;
import com.jeffreymanzione.collections.queues.Queue;
import com.jeffreymanzione.collections.queues.StrictQueue;

public class NaturalTreeSet<E extends Comparable<E>> extends
		AbstractSortedSet<E> {

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

		public boolean delete(E element, Node parent, Direction dir) {

//			System.out.println("Element=" + element + "\tParent="
//					+ (parent == null ? null : parent.getElement()) + "\tThis="
//					+ this.getElement());
			if (this.getElement().compareTo(element) == 0) {

				if (!this.hasLeft()) {
					if (!this.hasRight()) {
						if (parent == null) {
							head = null;
						} else {
							parent.set(dir, null);
						}
					} else {
						if (parent == null) {
							head = this.getRight();
						} else {
							parent.set(dir, this.getRight());
						}
					}
				} else if (!this.hasRight()) {
					if (parent == null) {
						head = this.getLeft();
					} else {
						parent.set(dir, this.getLeft());
					}
				} else {
					Bottom<TreeNode> maxLeft = this.getLeft().getMaxChild();

//					System.out.println("parent=" + maxLeft.parent + "\tchild="
//							+ maxLeft.child + "\t\tdir=" + maxLeft.dir);
					this.setElement(maxLeft.child.getElement());
					maxLeft.child.delete(maxLeft.child.getElement(),
							maxLeft.parent == null ? this : maxLeft.parent,
							maxLeft.dir == null ? Direction.Left : maxLeft.dir);

				}

				return true;
			} else if (this.getElement().compareTo(element) > 0) {
				if (this.hasLeft()) {
					return this.getLeft().delete(element, this, Direction.Left);
				} else {
					return false;
				}
			} else {
				if (this.hasRight()) {
					return this.getRight().delete(element, this,
							Direction.Right);
				} else {
					return false;
				}
			}

		}

		@SuppressWarnings("unchecked")
		@Override
		public Bottom<TreeNode> getMaxChild() {
			return (Bottom<TreeNode>) super.getMaxChild();
		}

		@SuppressWarnings("unchecked")
		@Override
		public Bottom<TreeNode> getMinChild() {
			return (Bottom<TreeNode>) super.getMinChild();
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

		@Override
		public boolean contains(E element) {
			// TODO Auto-generated method stub
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean insert(E element) {
		if (head == null) {
			head = new TreeNode(element);
			size++;
			return true;
		} else {
			return ((TreeNode) head).insert(element);
		}
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

	@Override
	public boolean containsAll(Collection<E> collection) {
		// TODO Auto-generated method stub
		return false;
	}

}
