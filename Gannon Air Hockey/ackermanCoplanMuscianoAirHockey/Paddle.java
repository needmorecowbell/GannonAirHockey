package ackermanCoplanMuscianoAirHockey; //COMMENTED

import javax.swing.JLabel;

public class Paddle extends JLabel{

	private static final long serialVersionUID = 673648286358715919L; //serial id to keep object properties
	private final int diameter = 50; //diameter of the paddle
	
	public int getRadius(){return diameter/2;} //accessor method to get the radius
	public int getDiameter(){return diameter;} //accessor method to get the diameter
	
	public int getCX(){return (this.getBounds().x + (diameter / 2));} //returns the center x coordinate
	public int getCY(){return (this.getBounds().y + (diameter / 2));} //returns the center y coordinate
}
