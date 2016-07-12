package ackermanCoplanMuscianoAirHockey; //COMMENTED

import javax.swing.JLabel;

public class Puck extends JLabel{

	private static final long serialVersionUID = 7028885027789799819L; //serial id to keep object properties
	private final int diameter = 30; //diameter of the puck
	
	public int getRadius(){return diameter/2;} //accessor method to get the radius
	public int getDiameter(){return diameter;} //accessor method to get the diameter
	
	public int getCX(){return (this.getBounds().x + (diameter / 2));} //returns the center x coordinate
	public int getCY(){return (this.getBounds().y + (diameter / 2));} //returns the center y coordinate
	
	public int getX(){return this.getBounds().x;} //returns the paint x coordinate
	public int getY(){return this.getBounds().y;} //returns the paint y coordinate
}
