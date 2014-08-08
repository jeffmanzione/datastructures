package com.jeffreymanzione.collections.stacks;

import java.util.EmptyStackException;

public class StrictStack<E> implements Stack<E> {

	private Node head;

	private int size;

	private class Node {
		E element;
		Node next;

		Node(E element) {
			super();
			this.element = element;
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
	public void clear() {
		head = null;
		size = 0;
	}

	@Override
	public void push(E element) {
		Node node = new Node(element);
		node.next = head;
		head = node;

		size++;
	}

	@Override
	public E pop() {

		if (head == null) {
			throw new EmptyStackException();
		} else {

			Node oldHead = head;

			head = head.next;

			size--;

			return oldHead.element;
		}
	}

	@Override
	public E peek() {
		if (head == null) {
			throw new EmptyStackException();
		} else {
			return head.element;
		}
	}

}
