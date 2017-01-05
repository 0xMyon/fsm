package com.github.myon.parser;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.github.myon.util.Tuple;

public class ParserTest {

	@Test
	public void test() {


		final List<Character> w = Util.string("abc");

		final Parser<Character,Character> a = Parser.satisfy('a');
		//final Parser<Character,Character> b = Parser.satisfy('b');

		Assert.assertEquals(
				Util.set(new Tuple<>('a', Util.string("bc"))),
				a.apply(w));

		Assert.assertEquals(
				Util.set(
						new Tuple<>(Util.string(""), Util.string("aaab")),
						new Tuple<>(Util.string("a"), Util.string("aab")),
						new Tuple<>(Util.string("aa"), Util.string("ab")),
						new Tuple<>(Util.string("aaa"), Util.string("b"))
						),
				a.many().apply(Util.string("aaab")));

		Assert.assertEquals(
				Util.set(
						new Tuple<>(Util.string("aaa"), Util.string(""))
						),
				a.many().just().apply(Util.string("aaa")));



	}

}
