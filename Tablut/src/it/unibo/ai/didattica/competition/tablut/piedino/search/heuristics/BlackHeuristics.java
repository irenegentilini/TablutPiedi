package it.unibo.ai.didattica.competition.tablut.piedino.search.heuristics;

import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.State.Pawn;

import java.util.Random;
public class BlackHeuristics extends Heuristics{

	public BlackHeuristics() {
		super();
	}

	@Override
	public double evaluateState(State state) {
		return 8.0/state.getNumberOf(Pawn.WHITE);
	}

}
