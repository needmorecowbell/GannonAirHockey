package ackermanCoplanMuscianoAirHockey; //COMMENTED

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Point;
import java.awt.Cursor;
import java.awt.Robot;
import java.awt.Color;
import java.awt.AWTException;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.JOptionPane;

//true width is 444, true height is 572
public class ComputerGAME {

	private String colorChoice, oppColorChoice;
	//Global variables for frame and its visual components
	private JFrame frame;
	private Puck puck;
	private JLabel userGoal, opponentGoal, userScore, opponentScore;
	private Paddle computerPaddle, userPaddle;
	
	//Global variables representing locations, speeds, vectors, and scores
	private double puckSpeed, puckVX, puckVY, puckDIRX, puckDIRY;
	private int puckX, puckY;
	private int userPaddleX, userPaddleY;
	private int yourNumGoals = 0, oppNumGoals = 0;
	private int userPaddleSpeed = 0, opponentPaddleSpeed = 0;
	private Point opponentPreviousPoint = new Point(0, 0);
	
	private Cursor blankCursor; //cursor that will be blank (this is the cursor used in the game)
	
	private AI ai; //AI to control the computer's paddle
	private Robot robot; //robot to move your mouse back to the reset position at the start and after a goal
	//private Recorder recorder;
	
	private final double FRICTION = 1.0045, ENERGY_TRANSFER = 1.00023;
	private final int PADDLE_DIAMETER = 50;
	
	public ComputerGAME(String colorChoice, int difficulty){
		
		//recorder = new Recorder("U:\\GAME.txt");
		ai = new AI(difficulty); //creates a new AI with the difficulty parameter
		this.colorChoice = colorChoice;
		setUp(); //calls the setUp method, which creates and fills the JFrame with its visual components
	}
	
	public void setUp(){
		
		//initializes some variables for speed and directions
		puckSpeed = 0;
		puckDIRX = 1;
		puckDIRY = 1;
		
		//sets up the robot for resetting the mouse
		try{
			this.robot = new Robot();
		}catch(AWTException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			JOptionPane.showMessageDialog(null, "Could not instantiate robot!", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
		//sets up basic JFrame properties
		frame = new JFrame("Air Hockey");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.addMouseMotionListener(new ML()); //adds the mouse motion listener to the frame
		
		//sets up and implements the blank cursor for the mouse
		BufferedImage cursorImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		this.blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		frame.setCursor(blankCursor);
		
		//sets up the puck with its image png
		puck = new Puck();
		puck.setBounds(222-puck.getRadius(), 286-puck.getRadius(), 30, 30);
		ImageIcon puckIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/puck.png"));
		puck.setIcon(puckIcon);
		
		//sets up the user's paddle with its image png
		userPaddle = new Paddle();
		ImageIcon userPaddleIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/" + colorChoice.toLowerCase() + "Paddle.png"));
		userPaddle.setIcon(userPaddleIcon);
		userPaddle.setBounds(222-userPaddle.getRadius(), 286+200-userPaddle.getRadius(), PADDLE_DIAMETER, PADDLE_DIAMETER);
		
		//sets up the computer's paddle with its image png
		computerPaddle = new Paddle();
		ImageIcon opponentPaddleIcon;
		if(colorChoice.equals("Blue")){
			opponentPaddleIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/redPaddle.png"));
		}else{
			opponentPaddleIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/bluePaddle.png"));
		}
		computerPaddle.setIcon(opponentPaddleIcon);
		computerPaddle.setBounds(222-computerPaddle.getRadius(), 286-200-computerPaddle.getRadius(), PADDLE_DIAMETER, PADDLE_DIAMETER);
		
		//sets up the user goal in the proper location
		userGoal = new JLabel();
		userGoal.setOpaque(true);
		switch(colorChoice)
		{
		case "Red": userGoal.setBackground(Color.red);
		break;
		case "Orange": userGoal.setBackground(new Color(234, 47, 1));
		break;
		case "Green": userGoal.setBackground(new Color(0, 154, 1));
		break;
		case "Pink": userGoal.setBackground(new Color(237, 33, 121));
		break;	
		case "Purple": userGoal.setBackground(new Color(151, 49, 154));
		break;	
		case "Blue": userGoal.setBackground(Color.blue);
		}
		userGoal.setBounds(157, 522, 130, 50);
		
		//sets up the opponent goal in the proper location
		opponentGoal = new JLabel();
		opponentGoal.setOpaque(true);
		if(colorChoice.equals("Blue")){
			opponentGoal.setBackground(Color.red);
		}else{
			opponentGoal.setBackground(Color.blue);
		}
		opponentGoal.setBounds(157, 0, 130, 50);
		
		//sets up the JLabel to represent your score
		userScore = new JLabel("You: " + yourNumGoals, JLabel.CENTER);
		userScore.setOpaque(false);
		userScore.setFont(new Font("Arial Bold", Font.BOLD, 15));
		switch(colorChoice)
		{
		case "Red": userScore.setForeground(Color.red);
		break;
		case "Orange": userScore.setForeground(new Color(234, 47, 1));
		break;
		case "Green": userScore.setForeground(new Color(0, 154, 1));
		break;
		case "Pink": userScore.setForeground(new Color(237, 33, 121));
		break;	
		case "Purple": userScore.setForeground(new Color(151, 49, 154));
		break;
		case "Blue": userScore.setForeground(Color.blue);
		break;
		}
		userScore.setBounds(15, 532, 90, 30);
		
		//sets up the JLabel to represent the computer's score
		opponentScore = new JLabel("Computer: " + oppNumGoals, JLabel.CENTER);
		opponentScore.setOpaque(false);
		opponentScore.setFont(new Font("Arial Bold", Font.BOLD, 15));
		if(colorChoice.equals("Blue")){	
			opponentScore.setForeground(Color.red);
		}else{
			opponentScore.setForeground(Color.blue);
		}
		opponentScore.setBounds(15, 10, 90, 30);
		
		//sets up the wooden board background with its image png
		JLabel board = new JLabel();
		board.setBounds(50, 50, 344, 472);
		ImageIcon boardIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/whitebackground.png"));
		board.setIcon(boardIcon);
		
		//Add a midline here
		//center y value is 236
		JLabel midline = new JLabel();
		midline.setOpaque(true);
		midline.setBackground(new Color(10,10,10));
		midline.setBounds(50,276,344,20);
		
		
		
		//sets up the dark wood background for the walls with its image jpg 
		JLabel walls = new JLabel();
		walls.setBounds(0, 0, 444, 572);
		ImageIcon wallsIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/border5.jpg"));
		walls.setIcon(wallsIcon);
		
		//adds all the components to the frame, finishes setting properties, and sets it visible
		frame.add(puck);
		frame.add(userPaddle);
		frame.add(computerPaddle);
		frame.add(userGoal);
		frame.add(opponentGoal);
		frame.add(userScore);
		frame.add(opponentScore);
		frame.add(midline);
		frame.add(board);
		frame.add(walls);
		frame.setSize(450, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		setMousePosition(); //sets the mouse position to where the paddle is
		run(); //calls the game loop
	}
	
	public void setMousePosition(){
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize(); //gets the dimension of the screen
		int x = ((int)dimension.getWidth() / 2); //finds the x position where the mouse needs to be
		int y = ((int)dimension.getHeight() / 2) + 186 - userPaddle.getRadius(); //finds the y position where the puck needs to be
		
		robot.mouseMove(x, y); //sets the mouse position to the proper x,y location
	}
	
	//GAME LOOP
	public void run(){
		Recorder recorder = new Recorder(" ");
		while(yourNumGoals < 7 && oppNumGoals < 7){ //the game runs until someone reaches seven goals
			
			ai.move(computerPaddle, puck, puckDIRX, puckDIRY, puckSpeed); //ai moves the computer paddle
			
			//checks if someone scores and acts accordingly
			if(checkIfUserGoal()){
				goal(opponentGoal);
			}else if(checkIfOpponentGoal()){
				goal(userGoal);
			}
			
			calculateOpponentPaddleSpeed(); //calculates the opponent paddle speed
			
			//gets the x and y locations of the puck
			puckX = puck.getX();
			puckY = puck.getY();
			
			//checks for wall collisions, moves the puck, and checks for wall collisions again
			wallCollision();
			puckMove();
			wallCollision();
			
			//sets the bounds of the puck and user paddle on the screen
			puck.setBounds(puckX, puckY, 30, 30);
			userPaddle.setBounds(userPaddleX, userPaddleY, 50, 50);

			friction(); //calls the friction method to act on the puck speed variable
			
			//optional to record game:
			//recorder.record(puckX, puckY, userPaddleX, userPaddleY, computerPaddle.getX(), computerPaddle.getY());
			
			//Thread.sleep regulates game speed (100 updates / second)
			try{
				Thread.sleep(10);
			}catch(InterruptedException e){}
			
			if(yourNumGoals>=5||oppNumGoals>=5)
			{
				recorder.record(puckX,puckY,userPaddleX, userPaddleY, computerPaddle.getX(), computerPaddle.getY());
			}
		}
		if(colorChoice.equals("Blue"))
		{
			oppColorChoice = "Red";
		}
		else
		{
			oppColorChoice = "Blue";
		}
		
		Playback p = new Playback(recorder.getLocations(), colorChoice, oppColorChoice);
		p.play();
		
		JOptionPane.showMessageDialog(null, "Game Over!"); //once someone reaches seven, display the JOptionPane saying game over
	}
	
	//method for calculating the speed of the opponent paddle based off of how far it has moved from the previous update
	public void calculateOpponentPaddleSpeed(){
		
		Point currentPoint = computerPaddle.getLocation(); //gets the current location of the computer paddle as a point
		
		//solves for the speed by dividing the distance between the paddle and its previous location by an arbitrarily determined factor
		opponentPaddleSpeed = (int)((currentPoint.distance(opponentPreviousPoint)) / ENERGY_TRANSFER); 
	
		opponentPreviousPoint = currentPoint; //sets the previous point to the current point for next time's calculations
		
	}
	
	public void friction(){
		
		puckSpeed /= FRICTION; //changes the puck speed by an arbitrarily determined factor 
	}
	
	//method for what happens if someone scores
	public void goal(JLabel goal){

		puck.setVisible(false); //sets the puck invisible
		puck.setBounds(0, 0, 30, 30); //moves the puck to the upper left corner
		Color normalColor = goal.getBackground(); //grabs the color of the goal and stores it into the normalColor variable
		
		if(goal.equals(userGoal)){ //if the puck went in your goal, add one to the computer goals total
			oppNumGoals++;
		}else{ //if the puck went in the computer's goal, add one to your goal total
			yourNumGoals++;
		}
		
		opponentScore.setText("Computer: " + oppNumGoals); //updates the opponent score JLabel
		userScore.setText("You: " + yourNumGoals); //updates your score JLabel
		
		//for loop to make the goal flash
		for(int x = 0; x < 25; x++){
			if((x % 2)== 0){ //if x is even, set the color to white
				goal.setBackground(Color.white);
			}else{ //if x is odd, set the color to the original color
				goal.setBackground(normalColor);
			}
			
			//Thread.sleep of 0.1 seconds keeps the flashing slow enough to be visible
			try{
				Thread.sleep(100);
			}catch(InterruptedException e){}
		}

		goal.setBackground(normalColor); //after the for loop finishes, make sure the goal is set back to its original color
		
		//reset everything to original positions
		puck.setVisible(true);
		puck.setBounds(222-15, 286-15, 30, 30);
		userPaddle.setBounds(222-25, 286+200-25, 50 ,50);
		computerPaddle.setBounds(222-25, 286-200-25, 50, 50);
		puckSpeed = 0;
		setMousePosition();
	}
	
	//method to see if the user scored
	public boolean checkIfUserGoal(){
		
		//if the y is less than 50 and the puck x is completely within the goal posts, return true
		if(puck.getY() <= 50){
			if((puck.getCX() - puck.getRadius()) >= 157 && (puck.getCX() + puck.getRadius()) <= 287){
				return true;
			}
		}
			
		return false; //otherwise, its not a goal, so return false
	}
	
	public boolean checkIfOpponentGoal(){
		
		//if the y is greater than 521 and the puck x is within the goal posts, return true
		if((puck.getY() + puck.getDiameter()) >= 521){
			if((puck.getCX() - puck.getRadius()) >= 157 && (puck.getCX() + puck.getRadius() <= 287)){
				return true;
			}
		}
		
		return false; //otherwise, its not a goal, so return false
		
	}
	
	public void puckStep(){

		puckVX = puckSpeed*puckDIRX; //calculates the puck VX (which is the displacement on the X axis) using the direction x variable and the speed variable
		puckVY = puckSpeed*puckDIRY; //calculates the puck VY (which is the displacement on the Y axis) using the direction y variable and the speed variable
		puckX += (int)puckVX; //adds the VX variable to the puck's X location
		puckY += (int)puckVY; //adds the VY variable to the puck's Y location
		
		wallCollision(); //checks for wall collisions
	}
	
	public void puckMove(){
		
		puckStep(); //calls the puck step method

		if(userPaddleCollision()){ //if the user paddle hits the puck
			
			//calculate the direction x and direction y variables based off of relative locations of puck and paddle
			puckDIRX = (puck.getCX() - userPaddle.getCX()) / (Math.sqrt(((puck.getCX() - userPaddle.getCX()) * (puck.getCX() - userPaddle.getCX())) + ((puck.getCY() - userPaddle.getCY()) * (puck.getCY() - userPaddle.getCY()))));
			puckDIRY = (puck.getCY() - userPaddle.getCY()) / (Math.sqrt(((puck.getCX() - userPaddle.getCX()) * (puck.getCX() - userPaddle.getCX())) + ((puck.getCY() - userPaddle.getCY()) * (puck.getCY() - userPaddle.getCY()))));

			//transfers paddle speed to the puck
			if(userPaddleSpeed < 4){ //if the user paddle speed is less than 4, add it to the current puck speed
				puckSpeed += userPaddleSpeed;
			}else if(userPaddleSpeed <= 13){ //otherwise, assign it as the puck speed
				puckSpeed = userPaddleSpeed;
			}else{ //this ensures the max puck speed is 13
				puckSpeed = 13;
			}
			puckStep(); //calls the puck step method so that the puck does not get stuck in the paddle

		}else if(opponentPaddleCollision()){ //if the opponent paddle hits the puck
			
			//calculate the direction x and direction y variables based off of relative locations of puck and paddle			
			puckDIRX = (puck.getCX() - computerPaddle.getCX()) / (Math.sqrt(((puck.getCX() - computerPaddle.getCX()) * (puck.getCX() - computerPaddle.getCX())) + ((puck.getCY() - computerPaddle.getCY()) * (puck.getCY() - computerPaddle.getCY()))));
			puckDIRY = (puck.getCY() - computerPaddle.getCY()) / (Math.sqrt(((puck.getCX() - computerPaddle.getCX()) * (puck.getCX() - computerPaddle.getCX())) + ((puck.getCY() - computerPaddle.getCY()) * (puck.getCY() - computerPaddle.getCY()))));

			//transfers paddle speed to the puck
			if(opponentPaddleSpeed < 4){ //if the computer paddle speed is less than 5, add it to the current puck speed
				puckSpeed += opponentPaddleSpeed;
			}else if(userPaddleSpeed <= 13){ //otherwise, assign it as the puck speed
				puckSpeed = opponentPaddleSpeed;
			}else{ //ensures the max puck speed is 13;
				puckSpeed = 13;
			}
			puckStep(); //calls the puck step method so that the puck does not get stuck in the paddle
		}
		
	}
	
	//method to calculate if the user paddle has collided with the puck using the distance formula between the two center points 
	public boolean userPaddleCollision(){
		
		double distance = Math.sqrt((puck.getCX() - userPaddle.getCX()) * (puck.getCX() - userPaddle.getCX()) + ((puck.getCY() - userPaddle.getCY()) * (puck.getCY() - userPaddle.getCY())));
		if(distance <= (puck.getRadius() + userPaddle.getRadius())){
			return true;
		}else{
			return false;
		}
	}
	
	//method to calculate if the computer paddle has collided with the puck using the distance formula between the two center points
	public boolean opponentPaddleCollision(){
		
		double distance = Math.sqrt((puck.getCX() - computerPaddle.getCX()) * (puck.getCX() - computerPaddle.getCX()) + ((puck.getCY() - computerPaddle.getCY()) * (puck.getCY() - computerPaddle.getCY())));
		if(distance <= (puck.getRadius() + computerPaddle.getRadius())){
			return true;
		}else{
			return false;
		}
	}
	
	//method for calculating whether there was a collision with the wall and changing directional variables accordingly
	public void wallCollision(){
		
		if(puck.getX() <= 50 && puck.getY() <= 50){ //upper left corner
			puckDIRX = Math.abs(puckDIRX);
			puckDIRY = Math.abs(puckDIRY);
		}else if(puck.getX() <= 50 && (puck.getY() + puck.getDiameter()) >= 522){ //bottom left corner
			puckDIRX= Math.abs(puckDIRX);
			puckDIRY= -Math.abs(puckDIRY);
		}else if((puck.getX() + puck.getDiameter()) >= 394 && puck.getY() <= 50){ //upper right corner
			puckDIRX= -Math.abs(puckDIRX);
			puckDIRY = Math.abs(puckDIRY);
		}else if((puck.getX() + puck.getDiameter()) >= 394 && (puck.getY() + puck.getDiameter()) >= 522){ //bottom right corner
			puckDIRX = -Math.abs(puckDIRX);
			puckDIRY = -Math.abs(puckDIRY);
		}else if(puck.getX() <= 50){ //left wall
			puckDIRX = Math.abs(puckDIRX);
		}else if((puck.getX() + puck.getDiameter()) >= 394){ //right wall
			puckDIRX = -Math.abs(puckDIRX);
		}else if(puck.getY() <= 50){ //top wall
			puckDIRY = Math.abs(puckDIRY);
		}else if((puck.getY() + puck.getDiameter()) >= 522){ //bottom wall
			puckDIRY = -Math.abs(puckDIRY);
		}
	}
	
	//Mouse Motion Listener class
	private class ML implements MouseMotionListener{

		private Point userPreviousPoint = new Point(0, 0); //initializes the point at 0,0 because it will be immediately changed

		//DO THE SAME THING FOR BOTH DRAGGED AND MOVED -- both call the mouseEvent method
		public void mouseDragged(MouseEvent e) {
			mouseEvent(e);
		}
		
		public void mouseMoved(MouseEvent e) {
			mouseEvent(e);
		}
		
		//method to handle movements or dragging of the mouse
		public void mouseEvent(MouseEvent e){
		
			//gets the mouse x coordinate and y coordinate and stores them into ints
			int mouseX = e.getPoint().x; 
			int mouseY = e.getPoint().y;
			
			//if else bounds the paddle to the game board
			if((mouseX - userPaddle.getRadius()) >= 50 && (mouseX + userPaddle.getRadius()) <= 394 && (mouseY - userPaddle.getDiameter()) >= 50 && ((mouseY) >= 332 && (mouseY)<=522)){ //if its inside the bounds
				frame.setCursor(blankCursor); //set the cursor invisible
				userPaddleX = mouseX - userPaddle.getRadius(); //set the user paddle's center x coordinate to the mouse's x location
				userPaddleY = mouseY - userPaddle.getDiameter(); //set the user paddle's center y coordinate to the mouse's y location
			}else{ //if its outside the bounds
				Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR); //set up the normal cursor
				frame.setCursor(cursor); //set the cursor to the normal cursor
			}
			
			userPaddleSpeed = (int)((e.getPoint().distance(userPreviousPoint)) / ENERGY_TRANSFER); //calculates the user paddle speed using the distance covered and the arbitrary speed factor
			
			userPreviousPoint = e.getPoint(); //sets the user previous point to the current point for next time's calculation
		}
	}
}
