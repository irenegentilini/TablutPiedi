package it.unibo.ai.didattica.competition.tablut.piedino.search.heuristics;

import java.util.Random;

import it.unibo.ai.didattica.competition.tablut.domain.State;

public class WhiteHeuristics extends Heuristics {

	public WhiteHeuristics() {
		super();
	}

	@Override
	public double evaluateState(State state) {
		return new Random().nextInt()*10;
	}

}
