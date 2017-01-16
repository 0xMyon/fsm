package com.github.myon.parser;

import java.util.List;
import java.util.Optional;

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
				a.any().apply(Util.string("aaab")));

		Assert.assertEquals(
				Util.set(
						new Tuple<>(Util.string("aaa"), Util.string("b"))
						),
				a.whole().apply(Util.string("aaab")));



		Assert.assertEquals(
				Util.set(
						new Tuple<>(Util.string("aaa"), Util.string(""))
						),
				a.many().just().apply(Util.string("aaa")));

		Assert.assertEquals(Util.set(
				new Tuple<>(Optional.of('a'), Util.string("")),
				new Tuple<>(Optional.empty(), Util.string("a"))
				),
				a.option().apply(Util.string("a")));

		Assert.assertEquals(Util.set(
				new Tuple<>(Optional.of('a'), Util.string(""))
				),
				a.consume().apply(Util.string("a")));


	}

	@Test
	public void testTokens() {

		final TokenType<Character> number = new TokenType<>(Character::isDigit);
		final TokenType<Character> ws = new TokenType<>(Character::isWhitespace);
		final TokenType<Character> operator = new TokenType<>(c -> c == '+' || c == '-');


		final Parser<Character,List<Token<Character>>> tokenizer = number.parser()
				.choice(ws.parser())
				.choice(operator.parser())
				.all();

		Assert.assertEquals(Util.set(
				new Tuple<>(Util.list(
						new Token<>(number, Util.string("123")),
						new Token<>(ws, Util.string(" ")),
						new Token<>(operator, Util.string("+")),
						new Token<>(ws, Util.string(" ")),
						new Token<>(number, Util.string("456")),
						new Token<>(ws, Util.string(" ")),
						new Token<>(operator, Util.string("-")),
						new Token<>(ws, Util.string(" ")),
						new Token<>(number, Util.string("789"))
						), Util.string(""))
				), tokenizer.apply(Util.string("123 + 456 - 789")));


		//final Parser<Token<Character>, Node> number_node = Parser.satisfy(number).apply(Node::new);
		//;
		//Parser<Token, Node> expr =

	}


}
