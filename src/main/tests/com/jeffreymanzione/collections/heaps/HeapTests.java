package com.jeffreymanzione.collections.heaps;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;

import com.jeffreymanzione.maps.heaps.BinaryHeap;
import com.jeffreymanzione.maps.heaps.FibonacciHeap;

public class HeapTests {

	@Test
	public void testFibonacci() {
		FibonacciHeap<Integer, Integer> fh = new FibonacciHeap<>();

		List<Integer> sorted = new ArrayList<>();

		for (int i = 0; i < 100; i++) {
			sorted.add(i);
		}

		List<Integer> unsorted = new ArrayList<>(sorted);
		Collections.shuffle(unsorted);

		unsorted.forEach(e -> fh.insert(e, e));

		for (int i = 50; i < 100; i++) {
			fh.decreasePriority(i - 100, i);
		}

		Integer prev = null;
		while (fh.size() > 0) {
			Integer cur = fh.delete();
			assertTrue(prev == null || cur >= prev);
		}

	}

	@Test
	public void testBinary() {
		BinaryHeap<Integer, Integer> fh = new BinaryHeap<>();

		List<Integer> sorted = new ArrayList<>();

		IntStream.range(0, 100).forEach(i -> sorted.add(i));

		List<Integer> unsorted = new ArrayList<>(sorted);
		Collections.shuffle(unsorted);

		unsorted.forEach(e -> fh.insert(e, e));

		Integer prev = null;
		while (fh.size() > 0) {
			Integer cur = fh.delete();
			//System.out.println(cur);
			assertTrue(prev == null || cur >= prev);
		}

	}
}
