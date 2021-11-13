package it.unibo.ai.didattica.competition.tablut.piedino.client;

import java.io.IOException;
import java.net.UnknownHostException;

public class BlackPiedinoTablutClient {

	public static void main(String[] ignore) throws UnknownHostException, IOException {
		String[] args= {"black","piedino","60","localhost"};
		//PiedinoTablutClient.main(args);
		PiedinoTablutClient client=new PiedinoTablutClient("black","piedino",60,"localhost");
		Thread t=new Thread(client);
		t.start();
	}
}
