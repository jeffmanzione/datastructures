package com.jeffreymanzione.collections;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jeffreymanzione.collections.queues.StrictQueue;

public class QueueTests {

	@Test
	public void testStrictQueue() {
		StrictQueue<String> sQueue = new StrictQueue<>();

		sQueue.enqueue("Dog");
		sQueue.enqueue("Cat");
		sQueue.enqueue("Mouse");
		sQueue.enqueue("Horse");
		sQueue.enqueue("Monkey");

		assertTrue(sQueue.size() == 5);

		assertEquals(sQueue.dequeue(), "Dog");
		assertTrue(sQueue.size() == 4);
		assertEquals(sQueue.dequeue(), "Cat");
		assertTrue(sQueue.size() == 3);
		assertEquals(sQueue.dequeue(), "Mouse");
		assertTrue(sQueue.size() == 2);
		assertEquals(sQueue.dequeue(), "Horse");
		assertTrue(sQueue.size() == 1);
		assertEquals(sQueue.dequeue(), "Monkey");
		assertTrue(sQueue.size() == 0);
		assertTrue(sQueue.isEmpty());

	}

}
