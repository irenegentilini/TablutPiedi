package it.unibo.ai.didattica.competition.tablut.piedino.client;

import java.io.IOException;
import java.net.UnknownHostException;

import it.unibo.ai.didattica.competition.tablut.client.TablutClient;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;
import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;
import it.unibo.ai.didattica.competition.tablut.piedino.search.PiedinoGameAshtonTablut;
import it.unibo.ai.didattica.competition.tablut.piedino.search.Player;
import it.unibo.ai.didattica.competition.tablut.domain.Action;
import java.lang.Double;

public class PiedinoTablutClient extends TablutClient {

	private int gameVariant;
	private boolean debug;
	private Player player;
	
	public PiedinoTablutClient(String role, String name, int timeout, String ipAddress,int gameVariant, boolean debug) throws UnknownHostException, IOException {
		super(role,name,timeout,ipAddress);
		this.gameVariant=gameVariant;
		this.debug=debug;
	}

	public PiedinoTablutClient(String player, String name, int timeout, String ipAddress) throws UnknownHostException, IOException {
		this(player,name,timeout,ipAddress,0,false);
	}
	
	public PiedinoTablutClient(String player, String name, String ipAddress) throws UnknownHostException, IOException {
		this(player,name,60,ipAddress,0,false);
	}
	
	public PiedinoTablutClient(String player, String name) throws UnknownHostException, IOException {
		this(player,name,60,"localhost",0,false);
	}
	
	
	@Override
	public void run() {
		System.out.println("_________________________________");
		System.out.println("                             :            :\r\n"
				+ "                             :            :\r\n"
				+ "                             :            :\r\n"
				+ "                             :            :\r\n"
				+ "                             :            :\r\n"
				+ "                            .'            :\r\n"
				+ "                        _.-\"              :\r\n"
				+ "                    _.-\"                  '.\r\n"
				+ "    ..__...____...-\"                       :\r\n"
				+ "   : \\_\\                                    :\r\n"
				+ "   :    .--\"                                 :\r\n"
				+ "   `.__/  .-\" _                               :\r\n"
				+ "      /  /  ,\" ,-                            .'\r\n"
				+ "     (_)(`,(_,'L_,_____       ____....__   _.'\r\n"
				+ "      \"' \"             \"\"\"\"\"\"\"          \"\"\"");
		System.out.println("Player name: " + this.getName());
		System.out.println("Role: "+this.getPlayer());
		System.out.println("Timeout: "+this.getTimeout());
		System.out.println("_________________________________");
		
		State state=null;
		aima.core.search.adversarial.Game<State,Action,Turn> rules=null;
		
		switch(gameVariant) {
		case 0: 
			state=new StateTablut();
			rules=new PiedinoGameAshtonTablut(0, -1, "logs", "white_ai", "black_ai"); //FIXME non usiamo la classe AshtonTablutGame ma usiamo un adapter o altro
			break;
		default:
			System.err.println("Game variant not recognized");
			System.exit(-1);
		}
		
		try {
			declareName();	
		}catch(Exception e) {
			e.printStackTrace();
		}
				
		player=new Player(rules, Double.MIN_VALUE,Double.MIN_VALUE,super.getTimeout()-2,debug);
		
		state.setTurn(State.Turn.WHITE);
		
		
		
		 // set type of game
        	while(true) {
	        try {
	            this.read();
	        } catch (ClassNotFoundException | IOException e1) {
	            e1.printStackTrace();
	            System.exit(1);
	        }
	
	        // print current state
	        state = this.getCurrentState();
	        System.out.println("Current state:");
	        System.out.println(state.toString());
	
	        // if WHITE
	        if (this.getCurrentState().getTurn().equals(StateTablut.Turn.WHITE)) {
		        if (this.getPlayer().equals(State.Turn.WHITE)) {
		            // if is my turn (WHITE)
	                // search the best move in search tree
	                Action a = player.makeDecision(state);
		            System.out.println(a);
	                try {
	                    this.write(a);
	                } catch (ClassNotFoundException | IOException e) {
	                    e.printStackTrace();
	                }
	            }
	
	            // if is turn of oppenent (BLACK)
	            else {
	                System.out.println("Waiting for your opponent move...\n");
	            }
	        }
	        // if BLACK
	        else if (this.getCurrentState().getTurn().equals(StateTablut.Turn.BLACK)) {
	        	// if is my turn (BLACK)
	        	if (this.getPlayer().equals(State.Turn.BLACK)) {
	        		// search the best move in search tree
	                Action a = player.makeDecision(state);
	                try {
	                    this.write(a);
	                } catch (ClassNotFoundException | IOException e) {
	                    e.printStackTrace();
	                }
	            }
	            else {
	                System.out.println("Waiting for your opponent move...\n");
	            }
	        } 
	
	        // if WHITEWIN 
	        else if (state.getTurn().equals(StateTablut.Turn.WHITEWIN)) {
	            System.out.println(this.getPlayer().equals(State.Turn.WHITE)?"YOU WON!":"YOU LOSE!");
	            System.exit(0);
	        }
	
	        // if BLACKWIN
	        else if (state.getTurn().equals(StateTablut.Turn.BLACKWIN)) {
	            System.out.println(this.getPlayer().equals(State.Turn.BLACK)?"YOU WON!":"YOU LOSE!");
	            System.exit(0);
	        }
	
	        // if DRAW
	        else if (state.getTurn().equals(StateTablut.Turn.DRAW)) {
	            System.out.println("DRAW!");
	            System.exit(0);
	        }
	     
		}
	}
	
}