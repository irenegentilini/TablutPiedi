package it.unibo.ai.didattica.competition.tablut.piedino.search;

import it.unibo.ai.didattica.competition.tablut.domain.Action;

import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;


public class Player  extends IterativeDeepeningAlphaBetaSearch<State,Action,Turn>{
	
	public Player(aima.core.search.adversarial.Game<State, Action, Turn> game, double utilMin, double utilMax,
			int time) {
		super(game, utilMin, utilMax, time);
	}
	
	/**
     * Method that estimates the value for (not necessarily
     * terminal) states. This implementation returns the utility value for
     * terminal states and heuristic value for non-terminal
     * states.
     */
    @Override
    protected double eval(State state, Turn player) {
        // needed to make heuristicEvaluationUsed = true, if the state evaluated isn't terminal
        super.eval(state, player);
        // return heuristic value for given state
        return game.getUtility(state, player);
    } 
    
    @Override
 	public Action makeDecision(State state) {
 		return super.makeDecision(state);
 	}	
	
}
