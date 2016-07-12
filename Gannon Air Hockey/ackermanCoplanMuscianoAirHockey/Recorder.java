package ackermanCoplanMuscianoAirHockey; //NEEDS COMMENTED BUT CURRENTLY NOT IN USE

import java.util.ArrayList;

public class Recorder {
	
	private ArrayList<Integer> locations;
	
	public Recorder(String filePath){	
		locations = new ArrayList<Integer>();
	}
	
	public void record(int puckX, int puckY, int userPaddleX, int userPaddleY, int opponentPaddleX, int opponentPaddleY){
		
		locations.add(puckX);
		locations.add(puckY);
		locations.add(userPaddleX);
		locations.add(userPaddleY);
		locations.add(opponentPaddleX);
		locations.add(opponentPaddleY);
	}
	
	public ArrayList<Integer> getLocations(){return locations;}
}
