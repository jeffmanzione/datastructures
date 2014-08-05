package com.jeffreymanzione.collections.stacks;

import static org.junit.Assert.*;

import java.util.EmptyStackException;
import org.junit.Test;

import com.jeffreymanzione.collections.stacks.Stack;
import com.jeffreymanzione.collections.stacks.StrictStack;

public class StackTests {

	@Test
	public void testStrictStack() {
		Stack<String> sStack = new StrictStack<>();

		try {
			sStack.pop();
			fail();
		} catch (EmptyStackException e) {
			// wtvr
		}

		sStack.push("Dog");
		sStack.push("Cat");
		sStack.push("Mouse");
		sStack.push("Horse");
		sStack.push("Monkey");

		assertTrue(sStack.size() == 5);
		assertEquals(sStack.pop(), "Monkey");
		assertTrue(sStack.size() == 4);
		assertEquals(sStack.pop(), "Horse");
		assertTrue(sStack.size() == 3);
		assertEquals(sStack.pop(), "Mouse");
		assertTrue(sStack.size() == 2);
		assertEquals(sStack.pop(), "Cat");
		assertTrue(sStack.size() == 1);
		assertEquals(sStack.pop(), "Dog");
		assertTrue(sStack.size() == 0);
		assertTrue(sStack.isEmpty());

		try {
			sStack.pop();
			fail();
		} catch (EmptyStackException e) {
			// wtvr
		}

	}

}
