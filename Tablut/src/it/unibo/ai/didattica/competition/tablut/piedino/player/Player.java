package it.unibo.ai.didattica.competition.tablut.piedino.player;

import it.unibo.ai.didattica.competition.tablut.domain.Action;

import it.unibo.ai.didattica.competition.tablut.domain.State;
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;


public class Player  extends IterativeDeepeningAlphaBetaSearch<State,Action,String>{
	
	public Player(aima.core.search.adversarial.Game<State, Action, String> game, double utilMin, double utilMax,
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
    protected double eval(State state, String player) {
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
