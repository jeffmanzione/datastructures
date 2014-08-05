package com.jeffreymanzione.collections.queues;

import java.util.NoSuchElementException;

public class StrictQueue<E> implements Queue<E> {

	private Node head;
	private Node tail;

	private int size;

	private class Node {
		E element;
		Node prev;

		Node(E element) {
			super();
			this.element = element;
		}

	}

	@Override
	public void enqueue(E element) {
		Node newb = new Node(element);
		if (head == null) {
			head = newb;
			tail = head;
		} else {
			head.prev = newb;
			head = newb;
		}
		size++;

	}

	@Override
	public E peek() {
		if (tail == null) {
			return null;
		} else {
		return tail.element;
		}
	}

	@Override
	public E dequeue() {

		if (tail == null) {
			throw new NoSuchElementException();
		} else {
			E element = tail.element;

			if (tail.prev == null) {
				head = null;
			}

			tail = tail.prev;

			size--;

			return element;
		}
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void clear() {
		head = null;
		tail = null;
		size = 0;
	}
}
