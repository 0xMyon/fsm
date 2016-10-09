package com.github.myon.fsmlib.fsm;

import org.junit.Assert;
import org.junit.Test;

import com.github.myon.fsmlib.container.SymetricSet;



public class FSMTest {

	@Test
	public void test() {


		final FiniteStateMachine<Character, SymetricSet<Character>> a =
				new FiniteStateMachine<>(SymetricSet.Factory(), 'a');

		final FiniteStateMachine<Character, SymetricSet<Character>> b =
				new FiniteStateMachine<>(SymetricSet.Factory(), 'b');


		System.out.println(a);
		System.out.println(b);

		Assert.assertEquals(false,a.contains());
		Assert.assertEquals(true, a.contains('a'));
		Assert.assertEquals(false,a.contains('b'));
		Assert.assertEquals(false, a.contains('a','a'));
		Assert.assertEquals(false, a.contains('a','b'));
		Assert.assertEquals(false, a.contains('b','a'));
		Assert.assertEquals(false, a.contains('b','b'));

		Assert.assertEquals(false,b.contains());
		Assert.assertEquals(false, b.contains('a'));
		Assert.assertEquals(true,b.contains('b'));
		Assert.assertEquals(false, b.contains('a','a'));
		Assert.assertEquals(false, b.contains('a','b'));
		Assert.assertEquals(false, b.contains('b','a'));
		Assert.assertEquals(false, b.contains('b','b'));

		FiniteStateMachine<Character, SymetricSet<Character>> x = a.concat(b);
		System.out.println(x);

		Assert.assertEquals(false, x.contains());
		Assert.assertEquals(false, x.contains('a'));
		Assert.assertEquals(false, x.contains('b'));
		Assert.assertEquals(false, x.contains('a','a'));
		Assert.assertEquals(true, x.contains('a','b'));
		Assert.assertEquals(false, x.contains('b','a'));
		Assert.assertEquals(false, x.contains('b','b'));

		x = a.concat(a);System.out.println(x);

		Assert.assertEquals(false, x.contains());
		Assert.assertEquals(false, x.contains('a'));
		Assert.assertEquals(false, x.contains('b'));
		Assert.assertEquals(true, x.contains('a','a'));
		Assert.assertEquals(false, x.contains('a','b'));
		Assert.assertEquals(false, x.contains('b','a'));
		Assert.assertEquals(false, x.contains('b','b'));

		x = b.concat(a);System.out.println(x);

		Assert.assertEquals(false, x.contains());
		Assert.assertEquals(false, x.contains('a'));
		Assert.assertEquals(false, x.contains('b'));
		Assert.assertEquals(false, x.contains('a','a'));
		Assert.assertEquals(false, x.contains('a','b'));
		Assert.assertEquals(true, x.contains('b','a'));
		Assert.assertEquals(false, x.contains('b','b'));

		x = b.concat(b);System.out.println(x);

		Assert.assertEquals(false, x.contains());
		Assert.assertEquals(false, x.contains('a'));
		Assert.assertEquals(false, x.contains('b'));
		Assert.assertEquals(false, x.contains('a','a'));
		Assert.assertEquals(false, x.contains('a','b'));
		Assert.assertEquals(false, x.contains('b','a'));
		Assert.assertEquals(true, x.contains('b','b'));

		x = a.iteration();System.out.println(x);

		Assert.assertEquals(false, x.contains());
		Assert.assertEquals(true, x.contains('a'));
		Assert.assertEquals(false, x.contains('b'));
		Assert.assertEquals(true, x.contains('a','a'));
		Assert.assertEquals(false, x.contains('a','b'));
		Assert.assertEquals(false, x.contains('b','a'));
		Assert.assertEquals(false, x.contains('b','b'));

		x = a.union(b);System.out.println(x);

		Assert.assertEquals(false, x.contains());
		Assert.assertEquals(true, x.contains('a'));
		Assert.assertEquals(true, x.contains('b'));
		Assert.assertEquals(false, x.contains('a','a'));
		Assert.assertEquals(false, x.contains('a','b'));
		Assert.assertEquals(false, x.contains('b','a'));
		Assert.assertEquals(false, x.contains('b','b'));

		x = a.complement();System.out.println(x);

		Assert.assertEquals(true, x.contains());
		Assert.assertEquals(false, x.contains('a'));
		Assert.assertEquals(true, x.contains('b'));
		Assert.assertEquals(true, x.contains('a','a'));
		Assert.assertEquals(true, x.contains('a','b'));
		Assert.assertEquals(true, x.contains('b','a'));
		Assert.assertEquals(true, x.contains('b','b'));


	}

}
