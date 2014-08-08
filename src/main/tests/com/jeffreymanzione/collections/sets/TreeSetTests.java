package com.jeffreymanzione.collections.sets;

import static org.junit.Assert.*;

import java.util.Comparator;

import org.junit.Test;

import com.jeffreymanzione.collections.sets.NaturalTreeSet;
import com.jeffreymanzione.collections.sets.Set;
import com.jeffreymanzione.collections.sets.TreeSet;

public class TreeSetTests {

	private static final Set<Integer> fillSetWithInts ( Set<Integer> set ) {

		set.insert( 45 );
		set.insert( 11 );
		set.insert( 15 );
		set.insert( 4 );
		set.insert( 51 );
		set.insert( 31 );
		set.insert( 2 );

		return set;

	}

	private void genericIntegerSetTests ( Set<Integer> set ) {

		assertNotNull( set );

		// System.out.println(set.size());
		assertTrue( set.size() == 7 );
		// System.out.println(set);

		assertTrue( set.delete( 4 ) );
		// System.out.println(set);
		assertTrue( set.size() == 6 );
		assertTrue( set.delete( 11 ) );
		// System.out.println(set);
		assertTrue( set.size() == 5 );
		assertTrue( set.delete( 15 ) );
		// System.out.println(set);
		assertTrue( set.size() == 4 );
		assertTrue( set.delete( 51 ) );
		// System.out.println(set);
		assertTrue( set.size() == 3 );
		assertTrue( set.delete( 45 ) );
		// System.out.println(set);
		assertTrue( set.size() == 2 );
		assertTrue( set.delete( 2 ) );
		// System.out.println(set);
		assertTrue( set.size() == 1 );
		assertTrue( set.delete( 31 ) );
		// System.out.println(set);
		assertTrue( set.size() == 0 );
		assertTrue( set.isEmpty() );
	}

	@Test
	public void testNaturalTreeSetInsertDeleteSizeEmpty () {
		genericIntegerSetTests( fillSetWithInts( new NaturalTreeSet<Integer>() ) );

	}

	@Test
	public void testGenericTreeSetInsertDeleteSizeEmpty () {
		TreeSet<Integer> set = new TreeSet<>();
		set.setComparator( new Comparator<Integer>() {

			@Override
			public int compare ( Integer o1, Integer o2 ) {
				return Integer.compare( o2, o1 );
			}

		} );

		genericIntegerSetTests( fillSetWithInts( set ) );
	}
}
