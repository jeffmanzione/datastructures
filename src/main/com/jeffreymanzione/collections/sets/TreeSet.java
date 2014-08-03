package com.jeffreymanzione.collections.sets;

import java.util.Comparator;
import java.util.Iterator;

import com.jeffreymanzione.collections.Collection;

public class TreeSet<E> extends AbstractSet<E> implements SortedSet<E> {

	private Comparator<E> comparator;

	private TreeNode head;

	private int size;

	enum Direction {
		Left, Right
	}

	private class TreeNode extends Node {

		public TreeNode(E element) {
			super(element);
		}

		public boolean insert(E element) {
			if (comparator.compare(element, this.getElement()) > 0) {
				if (this.hasRight()) {
					return this.getRight().insert(element);
				} else {
					this.setRight(new TreeNode(element));
					size++;
					return true;
				}
			} else if (comparator.compare(element, this.getElement()) < 0) {
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

		public boolean delete(E element, TreeNode parent, Direction dir) {
			if (comparator.compare(this.getElement(), element) == 0) {

				if (!this.hasLeft()) {
					if (!this.hasRight()) {
						if (parent == null) {
							head = null;
						} else {
							parent.set(dir, null);
						}
					} else {
						head = this.getRight();
					}
				} else if (!this.hasRight()) {
					if (parent == null) {
						head = this.getLeft();
					} else {
						parent.set(dir, this.getLeft());
					}
				} else {
					TreeNode maxLeft = this.getLeft().getMaxChild();
					this.setElement(maxLeft.getElement());
					this.getLeft().delete(maxLeft.getElement(), this,
							Direction.Left);

				}

				size--;

				return true;
			} else if (comparator.compare(this.getElement(), element) > 0) {
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
		public TreeNode getMaxChild() {
			return (TreeNode) super.getMaxChild();
		}

		@SuppressWarnings("unchecked")
		@Override
		public TreeNode getMinChild() {
			return (TreeNode) super.getMinChild();
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

		// public TreeNode get(Direction dir) {
		// if (dir == Direction.Left) {
		// return getLeft();
		// } else {
		// return getRight();
		// }
		// }

		public void set(Direction dir, TreeNode node) {
			if (dir == Direction.Left) {
				this.setLeft(node);
			} else {
				this.setRight(node);
			}
		}

		public boolean contains(E element) {
			int comp = comparator.compare(this.getElement(), element);
			if (comp == 0) {
				return true;
			} else if (comp < 0) {
				if (this.hasRight()) {
					return this.getRight().contains(element);
				} else {
					return false;
				}
			} else {
				if (this.hasLeft()) {
					return this.getLeft().contains(element);
				} else {
					return false;
				}
			}
		}

	}

	public TreeSet(Comparator<E> comparator) {
		this.comparator = comparator;
	}

	@Override
	public boolean delete(E element) {
		if (head == null) {
			return false;
		} else {
			return head.delete(element, null, null);
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
	public boolean insert(E element) {
		if (head == null) {
			head = new TreeNode(element);
			return true;
		} else {
			return head.insert(element);
		}
	}

	@Override
	public void clear() {
		size = 0;
		head = null;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
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
		return new Iterator<E>() {

			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public E next() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void remove() {
				// TODO Auto-generated method stub
			}

		};
	}
}
