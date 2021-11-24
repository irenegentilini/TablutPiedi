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
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		String pawn = "";
		String teamName = "Piedino";
		String ipAddress = "localhost";
		int timeout = 60;
		boolean debug = false;
		
		if(args.length < 1 || (!args[0].equalsIgnoreCase("black") && !args[0].equalsIgnoreCase("white"))) {
			System.out.println("You have to specify which role you want to play Black | White");
			System.exit(-1);
		} else {
				pawn = args[0];
		}
		
		if(args.length > 1) {
		
            try {
                timeout = Integer.parseInt(args[1]);
                if(timeout <= 2) {
                	System.out.println("Match timeout must be an integer number bigger than 2");
                	System.exit(-1);
                }
            } catch (Exception e){
                System.out.println("Match timeout must be an integer number bigger than 2");
                	e.printStackTrace();
	                System.exit(-1);   
            }
		}
              
        if(args.length > 2) {
            	ipAddress = args[2];
         }
		
		if(args.length > 3) {
            if(args[3].equalsIgnoreCase("debug")) {
                debug = true;
            } else {
                System.out.println(args[3] + " :not recognized, debug is now disabled");
                
            }
			
		}
	
        PiedinoTablutClient client = new PiedinoTablutClient(pawn, teamName, timeout, ipAddress,0 ,debug);
        client.run();

	
	}
	
	
	@Override
	public void run() {
		
		System.out.println("════════════════════════════════════════════════════════════════════");
		System.out.println("   ♥╣[-_-]╠♥♥╣[-_-]╠♥♥╣[-_-]╠♥♥╣[-_-]╠♥♥╣[-_-]╠♥♥╣[-_-]╠♥♥╣[-_-]╠♥");
		System.out.println("════════════════════════════════════════════════════════════════════");
		System.out.println("   ʅ ʕ•ᴥ•ʔ ʃ♥♥ʅ ʕ•ᴥ•ʔ ʃ♥♥ʅ ʕ•ᴥ•ʔ ʃ♥ʅ ʕ•ᴥ•ʔ ʃ♥♥ʅ ʕ•ᴥ•ʔ ʃ♥♥ʅ ʕ•ᴥ•ʔ ʃ");
		System.out.println("════════════════════════════════════════════════════════════════════");
		System.out.println(" ლ(=ↀωↀ=)ლ ლ(=ↀωↀ=)ლ ლ(=ↀωↀ=)ლ ლ(=ↀωↀ=)ლ ლ(=ↀωↀ=)ლ ლ(=ↀωↀ=)ლ");

		System.out.print(
				  "╔═══════════════════════════════════════════════════════════════════╗\n"+
				  "║                              :            :                       ║\n"							   	
				+ "║                              :            :                       ║\n"
				+ "║                              :            :                       ║\n"
				+ "║                              :            :                       ║\n"
				+ "║                             .'            :                       ║\n"
				+ "║                         _.-\"              :                       ║\n"
				+ "║                     _.-\"                  '.                      ║\n"
				+ "║      ..__...____...-\"                       :                     ║\n"
				+ "║    : \\_\\                                     :                    ║\n"
				+ "║    :    .--\"                                  :                   ║\n"
				+ "║    `.__/  .-\" _                               :                   ║\n"
				+ "║       /  /  ,\" ,-                             .'                  ║\n"
				+ "║      (_)(`,(_,'L_,_____       ____....__   _.'                    ║\n"
				+ "║         \"' \"             \"\"\"\"\"\"\"          \"\"\"                     ║\n");		  
		
		System.out.println(""
				+ "╠═══════════════════════════════════════════════════════════════════╣");
		
		//System.out.println("║  ʅ ʕ•ᴥ•ʔ ʃ♥♥ʅ ʕ•ᴥ•ʔ ʃ♥♥ʅ ʕ•ᴥ•ʔ ʃ♥♥ʅ ʕ•ᴥ•ʔ ʃ♥♥ʅ ʕ•ᴥ•ʔ ʃ♥♥ʅ ʕ•ᴥ•ʔ ʃ ║");
		System.out.println("║  Player name: " + this.getName()+"\t                                            ║");
		System.out.println("║  Role: "+this.getPlayer()+"	\t                                            ║");
		System.out.println("║  Timeout: "+this.getTimeout()+"\t                                                    ║");
		System.out.println("║  Debug: "+this.debug +"\t                                                    ║");
		System.out.println("╚═══════════════════════════════════════════════════════════════════╝");
		
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
				
		player=new Player(rules, Double.MIN_VALUE,Double.MAX_VALUE,super.getTimeout()-2,debug);
		
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