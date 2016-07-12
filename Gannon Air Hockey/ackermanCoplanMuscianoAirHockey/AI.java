package ackermanCoplanMuscianoAirHockey; //COMMENTED
//286 is center y coordinate
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Point;

public class AI { //WE NEED TO TEACH THE COMPUTER HOW TO BEAT SUPER DEFENSIVE PEOPLE

	//instance variables -- mostly used for calculations of predicting puck contact point
	private final double friction = 1.0045;
	private final int paddleSTARTY = 91;
	private double distance, dirx, diry, speed;
	private int difficulty, count;
	private double posx, posy, xc, yc;
	private boolean fastStrike = false; //controls how fast the computer hits the puck
	private boolean attacking = false; //this boolean represents whether or not attack mode has been activated
	
	//constructor takes in a difficulty parameter for how good the computer will be at defending and how often it will attack
	public AI(int difficulty){
		this.difficulty = difficulty;
	}
	
	//this method is called within the ComputerGAME loop to handle all movements of the AI
	public void move(Paddle computerPaddle, Puck puck, double puckDIRX, double puckDIRY, double puckSpeed){
		
		
			if(!attacking){ //if attack mode has not already been activated
				bound(computerPaddle);
				if(puckSpeed > (difficulty*2.1134)){ //puck is going fast so computer blocks defensively
					block(computerPaddle, puck);
				}else if(puck.getCY() <= 286-puck.getRadius()){ //puck is going slow enough and is in attacking zone(200) so computer calculates location then activates attacking mode
					calculate(computerPaddle, puck, puckDIRX, puckDIRY, puckSpeed); //calculates striking point 
				}else{ 
					//puck is going slow but is out of range so computer just waits	
				}
			}else{ //attack mode has already been activated so computer strikes puck
				if(fastStrike){
					fastStrike(computerPaddle);
				}else{
					normalStrike(computerPaddle);
				}
			
		}
			bound(computerPaddle);
	}
	
	private void fastStrike(Paddle computerPaddle){
		
		if(count < 15){ //wait for the first ten
			
		}else if(count <= 20){ //attack for the next 5
			computerPaddle.setBounds((int)(computerPaddle.getX()+(dirx*speed)), (int)(computerPaddle.getY()+(diry*speed)), 50, 50);
		}else if(count < 28){ //retreat towards original position
			computerPaddle.setBounds((int)(computerPaddle.getX()-(dirx*speed)), (int)(computerPaddle.getY()-(diry*speed)), 50, 50);
		}else{ //wait then break attacking mode
			if(count == 33){
				count = 0;
				attacking = false;
			}
		}
		
		
		count++; //increments the counter
	}
	
	private void normalStrike(Paddle computerPaddle){
		
		if(count < 20){	//for the first 15 updates, move to and on the 15th, strike the puck
			computerPaddle.setBounds((int)(computerPaddle.getX()+(dirx*speed)), (int)(computerPaddle.getY()+(diry*speed)), 50, 50);
		}else{ //for the next ten, retreat towards the original position
			computerPaddle.setBounds((int)(computerPaddle.getX()-(dirx*speed)), (int)(computerPaddle.getY()-(diry*speed)), 50, 50);
			if(count == 35){ //once it reaches 25 updates(the amount of time the striking and retreating takes), reset the counter and deactivate attacking mode
				count = 0;
				attacking = false;
			}
		}
		
		count++; //increments the counter
	}
	
	private void bound(Paddle computerPaddle)
	{
		if(computerPaddle.getCX()-computerPaddle.getRadius()<50)
		{
			computerPaddle.setBounds(51, computerPaddle.getY(),50,50);
		}
		else if((computerPaddle.getCX() + computerPaddle.getRadius()) > 394)
		{
			computerPaddle.setBounds(343, computerPaddle.getY(),50,50);
		}
		else if((computerPaddle.getCY() - computerPaddle.getRadius()) < 50)
		{
			computerPaddle.setBounds(computerPaddle.getX(), 51, 50,50);
		}
		else if((computerPaddle.getCY()) >= 286-computerPaddle.getRadius())
		{
			computerPaddle.setBounds(computerPaddle.getX(), 285-computerPaddle.getDiameter(), 50, 50);
		}
	}
	
	private void calculate(Paddle computerPaddle, Puck puck, double puckDIRX, double puckDIRY, double puckSpeed){
		
		//grabs current puck position
		posx = puck.getCX();
		posy = puck.getCY();
		xc = posx - puck.getRadius();
		yc = posy - puck.getRadius();
		
		//calculates future puck position fifteen updates from now, taking into account friction
		for(int x = 0; x < 15; x++){
			
			
			
			posx += (puckSpeed*puckDIRX)/friction;
			posy += (puckSpeed*puckDIRY)/friction;
			
			
			puckSpeed/=friction;
		}
		double difference;
		if(posx+puck.getRadius()>394)
		{
			difference = Math.abs(posx - 394);
			posx -= 2*difference;
			
		}
		else if(posx-puck.getRadius()<50)
		{
			difference = Math.abs(posx - 50);
			posx += 2*difference;
		}
		
		if(posy+puck.getRadius()>522)
		{
			difference = Math.abs(posy - 522);
			posy -= 2*difference;
		}
		else if(posy-puck.getRadius()<50)
		{
			difference = Math.abs(posy - 50);
			posy += 2*difference;
		}
		
		//calculates distance it needs to travel and average vector speed necessary to reach that spot.
		this.distance = new Point((int)posx, (int)posy).distance(new Point(computerPaddle.getCX(), computerPaddle.getCY()));
		double theta = Math.atan((posy - computerPaddle.getCY())/(posx - computerPaddle.getCX())); //theta is used to break the vector speed into vx and vy components

		this.dirx = Math.cos(theta); //x component of vector
		this.diry = Math.sin(theta); //y component of vector
		
		if(computerPaddle.getX() > posx){ //makes sure x is in the correct direction
			dirx = -Math.abs(dirx);
		}
		if(computerPaddle.getY() < posy){ //makes sure y is in the correct direction
			diry = Math.abs(diry);
		}
		
		if(Math.random() < (difficulty/10.0)){
			this.speed = distance/5;
			fastStrike = true;
		}else{
			this.speed = distance/15;
			fastStrike = false;
		}
		this.attacking = true; //activates attacking mode
		count = 0; //makes sure the counter is set to 0
	}
	
	private void block(Paddle computerPaddle, Puck puck){
		
		//if the puck is within the difficulty there is no need to move as otherwise it would just oscillate back and forth
		if(computerPaddle.getCX() - puck.getCX() <= difficulty && computerPaddle.getCX() - puck.getCX() >= -difficulty){
			
		}else if(computerPaddle.getCX() < puck.getCX()){ //if paddle x is less than puck x, move to the right by difficulty
			computerPaddle.setBounds(computerPaddle.getX() + difficulty, computerPaddle.getY(), computerPaddle.getDiameter(), computerPaddle.getDiameter());
		}else{ //if paddle x is greater than puck x, move to the left by difficulty
			computerPaddle.setBounds(computerPaddle.getX() - difficulty, computerPaddle.getY(), computerPaddle.getDiameter(), computerPaddle.getDiameter());
		}
		
		//this if statement helps it to recover back to the original y value after attacking by moving up or down accordingly in small increments
		if(computerPaddle.getY() < paddleSTARTY){
			computerPaddle.setBounds(computerPaddle.getX(), computerPaddle.getY()+1, computerPaddle.getDiameter(), computerPaddle.getDiameter());
		}else if(computerPaddle.getY() > paddleSTARTY){
			computerPaddle.setBounds(computerPaddle.getX(), computerPaddle.getY()-1, computerPaddle.getDiameter(), computerPaddle.getDiameter());
		}
	}
}
