package com.jeffreymanzione.collections;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jeffreymanzione.collections.sets.NaturalTreeSet;
import com.jeffreymanzione.collections.sets.Set;

public class TreeSetTests {

	@Test
	public void testNaturalTreeSet() {
		Set<Integer> treeSet = new NaturalTreeSet<>();

		assertTrue(treeSet.isEmpty());
		treeSet.insert(45);
		treeSet.insert(11);
		treeSet.insert(15);
		treeSet.insert(4);
		treeSet.insert(51);
		treeSet.insert(31);
		treeSet.insert(2);
		System.out.println(treeSet.size());
		assertTrue(treeSet.size() == 7);
		System.out.println(treeSet);

		treeSet.delete(4);
		assertTrue(treeSet.size() == 6);
		treeSet.delete(11);
		assertTrue(treeSet.size() == 5);
		treeSet.delete(15);
		assertTrue(treeSet.size() == 4);
		treeSet.delete(51);
		assertTrue(treeSet.size() == 3);
		
	}

}
