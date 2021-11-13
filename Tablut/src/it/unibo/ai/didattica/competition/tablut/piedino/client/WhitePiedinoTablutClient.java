package it.unibo.ai.didattica.competition.tablut.piedino.client;

import java.io.IOException;
import java.net.UnknownHostException;

public class WhitePiedinoTablutClient {

	public static void main(String[] ignore) throws UnknownHostException, IOException {
		String[] args= {"white","piedino","60","localhost"};
		//PiedinoTablutClient.main(args);
		PiedinoTablutClient client=new PiedinoTablutClient("white","piedino",5,"localhost",0,true);
		Thread t=new Thread(client);
		t.start();
	}
}