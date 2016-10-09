package com.github.myon.fsmlib.fsm;

import java.util.HashMap;
import java.util.Map;

import com.github.myon.fsmlib.container.FiniteSet;
import com.github.myon.fsmlib.container.Sequence;
import com.github.myon.fsmlib.immutable.ClosedLanguage;
import com.github.myon.fsmlib.immutable.ClosedSymetricSet;
import com.github.myon.util.Tuple;

public class FiniteStateMachine<O, T extends ClosedSymetricSet<O, T>> implements ClosedLanguage<O, FiniteStateMachine<O, T>> {


	public static <O, T extends ClosedSymetricSet<O, T>> FiniteStateMachine<O,T> empty() {
		return new FiniteStateMachine<>(false);
	}

	public static <O, T extends ClosedSymetricSet<O, T>> FiniteStateMachine<O,T> epsilon() {
		return new FiniteStateMachine<>(true);
	}

	private FiniteStateMachine(final T type, final boolean epsilon) {
		this(epsilon);
		this.transition(this.initial, type, this.finals);
	}

	private FiniteStateMachine(final boolean epsilon) {
		if (epsilon) {
			this.finals.add(this.initial);
		}
	}

	/**
	 * initial state
	 */
	private final State initial = this.state();

	/**
	 * final states
	 */
	private final FiniteSet<State> finals = new FiniteSet<>(this.state());

	/**
	 * all states
	 */
	private final FiniteSet<State> states = new FiniteSet<>();


	private final FiniteSet<Transition> delta = new FiniteSet<>();


	/**
	 * creates a new state
	 * @return
	 */
	private State state() {
		final State result = new State();
		this.states.add(result);
		return result;
	}

	private void transition(final FiniteSet<State> source, final T type, final FiniteSet<State> target) {
		source.forAll((final State s) -> this.transition(s, type, target));
	}

	private void transition(final State source, final T type, final FiniteSet<State> target) {
		target.forAll((final State s) -> this.transition(source, type, s));
	}

	private void transition(final FiniteSet<State> source, final T type, final State target) {
		source.forAll((final State s) -> this.transition(s, type, target));
	}

	private void transition(final FiniteSet<State>  source, final FiniteSet<State> target) {
		target.forAll((final State t) -> this.transition(source, t));

	}

	private void transition(final FiniteSet<State> source, final State target) {
		source.forAll((final State s) -> this.transition(s, target));
	}

	private void transition(final State source, final State target) {
		if (source == target) {
			return;
		}

		source.source().forAll((transition)->{
			this.transition(transition.source, transition.type, target);
		});

		target.target().forAll((transition)->{
			this.transition(source, transition.type, transition.target);
		});

		source.loop().forAll((transition)->{
			this.transition(target, transition.type, target);
		});

	}

	private class State {



		public boolean isFinal() {
			return FiniteStateMachine.this.finals.contains(this);
		}

		public boolean isInitial() {
			return FiniteStateMachine.this.initial == this;
		}

		public FiniteSet<Transition> source() {
			return FiniteStateMachine.this.delta.findAll((t)->t.source != State.this && t.target == State.this);
		}

		public FiniteSet<Transition> target() {
			return FiniteStateMachine.this.delta.findAll((t)->t.source == State.this && t.target != State.this);
		}

		public FiniteSet<Transition> loop() {
			return FiniteStateMachine.this.delta.findAll((t)->t.source == State.this && t.target == State.this);
		}

		public FiniteSet<Transition> next() {
			return FiniteStateMachine.this.delta.findAll((t)->t.source == State.this);
		}

	}

	private void transition(final State source, final T type, final State target) {
		final Transition transition = this.delta.findFirst((t)->t.source == source && t.target == target);
		if (transition == null) {
			this.delta.add(new Transition(source, type, target));
		} else {
			transition.type = transition.type.union(type);
		}
	}

	private class Transition {

		public Transition(final State source, final T type, final State target) {
			this.source = source;
			this.type = type;
			this.target = target;
		}

		public final State source, target;
		public T type;



	}


	private class Configuration {

		private final FiniteSet<State> state = new FiniteSet<>(FiniteStateMachine.this.initial);

		public void step(final O object) {
			this.state.<FiniteSet<State>>aggregate(()->new FiniteSet<>(), (state, result)->{
				return result.union(state.next().findAll((t)->t.type.contains(object)).map((t)->t.target));
			});
		}

		public boolean run(final Sequence<O> word) {
			word.forAll((o)->this.step(o));
			return !this.state.isDisjunced(FiniteStateMachine.this.finals);
		}

	}


	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFinite() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(final FiniteStateMachine<O, T> that) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEpsilon() {
		// TODO Auto-generated method stub
		return false;
	}




	@Override
	public boolean contains(final Sequence<O> object) {
		return new Configuration().run(object);
	}

	public boolean containsEpsilon() {
		return this.initial.isFinal();
	}



	@Override
	public FiniteStateMachine<O, T> complement() {
		final FiniteStateMachine<O, T> result = new FiniteStateMachine<>(this.containsEpsilon());
		final Tuple<State,FiniteSet<State>> s = result.add(this);

		final State bot = result.state();

		result.states.forAll((state)->{
			result.transition(
					state,
					state.target().<T>aggregate(()->null, (t,r)->r.intersection(t.type)), // TODO replace the null
					bot);
		});

		result.finals.invert(result.states);
		result.cleanup();
		return result;
	}

	@Override
	public FiniteStateMachine<O, T> iteration() {
		final FiniteStateMachine<O, T> result = new FiniteStateMachine<>(this.containsEpsilon());
		final Tuple<State,FiniteSet<State>> s = result.add(this);
		result.transition(result.initial, s.source);
		result.transition(s.target, s.source);
		result.transition(s.target, result.finals);
		result.cleanup();
		return result;
	}

	@Override
	public FiniteStateMachine<O, T> option() {
		final FiniteStateMachine<O, T> result = new FiniteStateMachine<>(true);
		final Tuple<State,FiniteSet<State>> s = result.add(this);
		result.transition(result.initial, s.source);
		result.transition(s.target, result.finals);
		result.cleanup();
		return result;
	}

	@Override
	public FiniteStateMachine<O, T> concat(final FiniteStateMachine<O, T> that) {
		final FiniteStateMachine<O, T> result = new FiniteStateMachine<>(this.containsEpsilon() && that.containsEpsilon());
		final Tuple<State, FiniteSet<State>> left = result.add(this);
		final Tuple<State, FiniteSet<State>> right = result.add(that);
		result.transition(result.initial, left.source);
		result.transition(left.target, right.source);
		result.transition(right.target, result.finals);
		result.cleanup();
		return result;
	}

	@Override
	public FiniteStateMachine<O, T> union(final FiniteStateMachine<O, T> that) {
		final FiniteStateMachine<O, T> result = new FiniteStateMachine<>(this.containsEpsilon() || that.containsEpsilon());
		final Tuple<State, FiniteSet<State>> left = result.add(this);
		final Tuple<State, FiniteSet<State>> right = result.add(that);
		result.transition(result.initial, left.source);
		result.transition(result.initial, right.source);
		result.transition(right.target, result.finals);
		result.transition(left.target, result.finals);
		result.cleanup();
		return result;
	}

	private Tuple<State, FiniteSet<State>> add(final FiniteStateMachine<O, T> that) {
		final Map<State, State> map = new HashMap<>();
		that.states.forAll((s)-> map.put(s, this.state()));
		that.delta.forAll((t)-> {
			this.transition(map.get(t.source), t.type, map.get(t.target));
		});
		return new Tuple<>(map.get(that.initial), that.finals.map((s)->map.get(s)));
	}

	private void cleanup() {
		// TODO Auto-generated method stub

	}

}
