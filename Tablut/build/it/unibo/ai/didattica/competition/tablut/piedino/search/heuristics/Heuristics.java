package it.unibo.ai.didattica.competition.tablut.piedino.search.heuristics;

import it.unibo.ai.didattica.competition.tablut.domain.State;

public abstract class Heuristics {
	protected State state;
	
	public Heuristics() {
	}
	
	public abstract double evaluateState(State state);
}
