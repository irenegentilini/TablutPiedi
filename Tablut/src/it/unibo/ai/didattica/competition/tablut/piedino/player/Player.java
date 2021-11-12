package it.unibo.ai.didattica.competition.tablut.piedino.player;

import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.Game;
import it.unibo.ai.didattica.competition.tablut.domain.State;

public abstract class Player {
	
	private String role;
	private Game rules;
	private State state;
	
	public Player (String role,Game rules, State state) {
		this.role=role;
		this.rules=rules;
		this.state=state; //FIXME SENTO CHE NON E' NECESSARIO
	}
	
	public String getRole() {
		return role;
	}
	
	//Funzione che conosce le rules e lo stato e ritorna l'azione migliore sapendo la sua euristica
	public abstract Action makeAction(State state);
	
}
