package com.github.myon.parser;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.github.myon.util.Tuple;

public interface Parser<T, R> extends Function<List<T>, Set<Tuple<R,List<T>>>> {

	public static <T,R> Parser<T,R> fail() {
		return (word)->Util.set();
	}

	public static <T,R> Parser<T,R> succeed(final Supplier<R> supplier) {
		return (word)->Util.set(new Tuple<>(supplier.get(), word));
	}

	public static <T> Parser<T,T> satisfy(final T object) {
		return Parser.satisfy((c)->c.equals(object));
	}

	public static <T> Parser<T,T> satisfy(final Predicate<T> predicate) {
		return (word)->(
				(word.size() > 0) && predicate.test(word.get(0)) ?
						Util.set(new Tuple<>(word.get(0), word.subList(1, word.size()))) :
							Util.set()
				);
	}



	public default Parser<T,R> choice(final Parser<T,R> that) {
		return (word)->Util.set(Parser.this.apply(word), that.apply(word));
	}


	public default <S> Parser<T,Tuple<R,S>> sequence(final Parser<T,S> that) {
		return (word)->Util.aggregate(Parser.this.apply(word), (r)-> Util.map(that.apply(r.target), (s)->{
			return new Tuple<>(new Tuple<>(r.source,s.source), s.target);
		}));
	}


	public default Parser<T,List<R>> list(final Parser<T, R> that) {
		return this.sequence(that).apply((t)->Util.list(t.source, t.target));
	}


	public default Parser<T,List<R>> many() {
		return new Parser<T, List<R>>() {
			@Override
			public Set<Tuple<List<R>, List<T>>> apply(final List<T> word) {
				return Util.set(Util.aggregate(Parser.this.apply(word), (final Tuple<R,List<T>> t)-> {
					return Util.map(this.apply(t.target), (final Tuple<List<R>,List<T>> x)->{
						return new Tuple<>(Util.list(t.source, x.source),x.target);
					});
				}), Util.set(new Tuple<>(new LinkedList<>(), word)));
			}
		};
	}

	public default <S,U> Parser<T,U> aggregate(final Parser<T,S> that, final BiFunction<R,S,U> function) {
		return this.sequence(that).apply((t)->function.apply(t.source, t.target));
	}

	public default <S> Parser<T,S> apply(final Function<R,S> function) {
		return (word)->Util.map(Parser.this.apply(word), (t)->new Tuple<>(function.apply(t.source), t.target));
	}

	public default <S> Parser<T,R> left(final Parser<T,S> that) {
		return this.sequence(that).apply((t)->t.source);
	}

	public default <S> Parser<T,S> right(final Parser<T,S> that) {
		return this.sequence(that).apply((t)->t.target);
	}

	public default Parser<T,R> filter(final BiPredicate<R,List<T>> predicate) {
		return (word)->Util.filter(this.apply(word), (x)->predicate.test(x.source,x.target));
	}

	public default Parser<T,R> just() {
		return this.filter((x,y)->y.isEmpty());
	}


}
