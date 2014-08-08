package com.jeffreymanzione.collections.sets;

import java.util.Comparator;
import java.util.Iterator;

public class TreeSet<E> extends AbstractSortedSet<E> {

	private Comparator<E>	comparator;

	private class TreeNode extends Node {

		public TreeNode ( E element ) {
			super( element );
		}

		public boolean insert ( E element ) {
			if ( comparator.compare( element, this.getElement() ) > 0 ) {
				if ( this.hasRight() ) {
					return this.getRight().insert( element );
				} else {
					this.setRight( new TreeNode( element ) );
					size++;
					return true;
				}
			} else if ( comparator.compare( element, this.getElement() ) < 0 ) {
				if ( this.hasLeft() ) {
					return this.getLeft().insert( element );
				} else {
					this.setLeft( new TreeNode( element ) );
					size++;
					return true;
				}
			} else {
				return false;
			}

		}

		public boolean delete ( E element, Node parent, Direction dir ) {

//			System.out.println( "Element=" + element + "\tParent="
//					+ (parent == null ? null : parent.getElement()) + "\tThis="
//					+ this.getElement() );
			if ( comparator.compare( this.getElement(), element ) == 0 ) {

				if ( !this.hasLeft() ) {
					if ( !this.hasRight() ) {
						if ( parent == null ) {
							head = null;
						} else {
							parent.set( dir, null );
						}
					} else {
						if ( parent == null ) {
							head = this.getRight();
						} else {
							parent.set( dir, this.getRight() );
						}
					}
				} else if ( !this.hasRight() ) {
					if ( parent == null ) {
						head = this.getLeft();
					} else {
						parent.set( dir, this.getLeft() );
					}
				} else {
					Bottom<TreeNode> maxLeft = this.getLeft().getMaxChild();

//					System.out.println( "parent=" + maxLeft.parent + "\tchild="
//							+ maxLeft.child + "\t\tdir=" + maxLeft.dir );
					this.setElement( maxLeft.child.getElement() );
					maxLeft.child.delete( maxLeft.child.getElement(),
							maxLeft.parent == null ? this : maxLeft.parent,
							maxLeft.dir == null ? Direction.Left : maxLeft.dir );

				}

				return true;
			} else if ( comparator.compare( this.getElement(), element ) > 0 ) {
				if ( this.hasLeft() ) {
					return this.getLeft()
							.delete( element, this, Direction.Left );
				} else {
					return false;
				}
			} else {
				if ( this.hasRight() ) {
					return this.getRight().delete( element, this,
							Direction.Right );
				} else {
					return false;
				}
			}

		}

		@SuppressWarnings("unchecked")
		@Override
		public Bottom<TreeNode> getMaxChild () {
			return (Bottom<TreeNode>) super.getMaxChild();
		}

		@SuppressWarnings("unchecked")
		@Override
		public Bottom<TreeNode> getMinChild () {
			return (Bottom<TreeNode>) super.getMinChild();
		}

		@SuppressWarnings("unchecked")
		@Override
		public TreeNode getLeft () {
			return (TreeNode) super.getLeft();
		}

		@SuppressWarnings("unchecked")
		@Override
		public TreeNode getRight () {
			return (TreeNode) super.getRight();
		}

		public boolean contains ( E element ) {
			int comp = comparator.compare( this.getElement(), element );
			if ( comp == 0 ) {
				return true;
			} else if ( comp < 0 ) {
				if ( this.hasRight() ) {
					return this.getRight().contains( element );
				} else {
					return false;
				}
			} else {
				if ( this.hasLeft() ) {
					return this.getLeft().contains( element );
				} else {
					return false;
				}
			}
		}

	}

	public TreeSet ( Comparator<E> comparator ) {
		this.comparator = comparator;
	}

	public TreeSet () {
	}

	public void setComparator ( Comparator<E> comparator ) {
		this.comparator = comparator;
	}

	@Override
	public Iterator<E> iterator () {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E first () {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E last () {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<E> forward () {
		return this.iterator();
	}

	@Override
	public Iterator<E> backward () {
		return new Iterator<E>() {

			@Override
			public boolean hasNext () {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public E next () {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void remove () {
				// TODO Auto-generated method stub
			}

		};
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean insert ( E element ) {
		if ( head == null ) {
			head = new TreeNode( element );
			size++;
			return true;
		} else {
			return ((TreeNode) head).insert( element );
		}
	}
}
