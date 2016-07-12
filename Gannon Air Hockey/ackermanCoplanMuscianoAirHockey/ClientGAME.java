package ackermanCoplanMuscianoAirHockey; //COMMENTED

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Cursor;
import java.awt.Robot;
import java.awt.AWTException;
import java.awt.image.BufferedImage;
import java.awt.Toolkit;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.Point;

import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;

import javax.swing.JOptionPane;

public class ClientGAME {

	private String colorChoice, oppColorChoice;
	//global variables
	private JFrame frame;
	private Puck puck;
	private JLabel userGoal, opponentGoal, userScore, opponentScore;
	private Paddle userPaddle, opponentPaddle;

	private int yourNumGoals = 0, oppNumGoals = 0;
	private int yourPaddleX, yourPaddleY;
	private Cursor blankCursor;
	
	
	private Robot robot;
	private TwoPersonClient client;

	public ClientGAME(TwoPersonClient client, String colorChoice){

		this.colorChoice = colorChoice;
		this.client = client;
		oppColorChoice = client.getOpponentColor(colorChoice);
		setUp();
	}

	public void setUp(){
		
		//sets up the robot so it can reset mouse + paddle positions between goals
		try{
			this.robot = new Robot();
		}catch(AWTException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			JOptionPane.showMessageDialog(null, "Could not instantiate robot!", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
		//basic JFrame properties
		frame = new JFrame("Air Hockey");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.addMouseMotionListener(new ML()); //adds the mouse motion listener so the paddle follows the mouse

		//sets up the blank cursor for the mouse that way the cursor is the paddle
		BufferedImage cursorImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		this.blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		frame.setCursor(blankCursor);

		//sets up the puck, including its png
		puck = new Puck();
		puck.setBounds(222-puck.getRadius(), 286-puck.getRadius(), 30, 30);
		ImageIcon puckIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/puck.png"));
		puck.setIcon(puckIcon);

		//sets up the user paddle, including its png
		userPaddle = new Paddle();
		ImageIcon userPaddleIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/" + client.getOppColorChoice().toLowerCase() + "Paddle.png"));
		userPaddle.setIcon(userPaddleIcon);
		userPaddle.setBounds(222-userPaddle.getRadius(), 286+200-userPaddle.getRadius(), 50, 50);

		//sets up the opponent paddle, including its png
		//THE PERSON PLAYING THE ClientGAME IS THE OPPONENT PADDLE
		opponentPaddle = new Paddle();
		ImageIcon opponentPaddleIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/" + colorChoice.toLowerCase() + "Paddle.png"));
		opponentPaddle.setIcon(opponentPaddleIcon);
		opponentPaddle.setBounds(222-opponentPaddle.getRadius(), 286-255-opponentPaddle.getRadius(), 50, 50);

		//sets up the user goal (needs a png)
		userGoal = new JLabel();
		userGoal.setOpaque(true);
		switch(client.getOppColorChoice())
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

		//sets up the opponent goal (needs a png)
		opponentGoal = new JLabel();
		opponentGoal.setOpaque(true);
		switch(colorChoice)
		{
		case "Red": opponentGoal.setBackground(Color.red);
		break;
		case "Orange": opponentGoal.setBackground(new Color(234, 47, 1));
		break;
		case "Green": opponentGoal.setBackground(new Color(0, 154, 1));
		break;
		case "Pink": opponentGoal.setBackground(new Color(237, 33, 121));
		break;	
		case "Purple": opponentGoal.setBackground(new Color(151, 49, 154));
		break;
		case "Blue": opponentGoal.setBackground(Color.blue);
		break;
		}
		opponentGoal.setBounds(157, 0, 130, 50);

		//sets up the user score including the user's name
		userScore = new JLabel(client.getOpponentName() + ": " + yourNumGoals, JLabel.CENTER);
		userScore.setOpaque(false);
		userScore.setFont(new Font("Arial Bold", Font.BOLD, 15));
		oppColorChoice = client.getOppColorChoice();
		switch(oppColorChoice)
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

		//sets up the opponent score, including the opponent's name
		opponentScore = new JLabel(client.getYourName() + ": " + oppNumGoals, JLabel.CENTER);
		opponentScore.setOpaque(false);
		opponentScore.setFont(new Font("Arial Bold", Font.BOLD, 15));
		switch(colorChoice)
		{
		case "Red": opponentScore.setForeground(Color.red);
		break;
		case "Orange": opponentScore.setForeground(new Color(234, 47, 1));
		break;
		case "Green": opponentScore.setForeground(new Color(0, 154, 1));
		break;
		case "Pink": opponentScore.setForeground(new Color(237, 33, 121));
		break;	
		case "Purple": opponentScore.setForeground(new Color(151, 49, 154));
		break;
		case "Blue": opponentScore.setForeground(Color.blue);
		break;
		}
		opponentScore.setBounds(15, 10, 90, 30);
		
		//represents the wood board background using a png
		JLabel back = new JLabel();
		back.setBounds(50, 50, 344, 472);
		ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/whitebackground.png"));
		back.setIcon(icon);
		
		//Add a midline here
		//center y value is 236
		JLabel midline = new JLabel();
		midline.setOpaque(true);
		midline.setBackground(new Color(10,10,10));
		midline.setBounds(50,276,344,20);
		
		//represents the darker wood walls using a png
		JLabel walls = new JLabel();
		walls.setBounds(0, 0, 444, 572);
		ImageIcon wallsIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/border5.jpg"));
		walls.setIcon(wallsIcon);

		//adds all components and sets the frame visible
		frame.add(puck);
		frame.add(userPaddle);
		frame.add(opponentPaddle);
		frame.add(userScore);
		frame.add(opponentScore);
		frame.add(opponentPaddle);
		frame.add(userGoal);
		frame.add(opponentGoal);
		frame.add(midline);
		frame.add(back);
		frame.add(walls);
		frame.setSize(450, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		setMousePosition(); //sets your mouse position so the paddle is positioned properly
		
		run(); //calls the game loop
	}
	
	//method to reset your mouse position to where the paddle should be
	public void setMousePosition(){
		
		//finds the proper x,y location that mouse should be moved to
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = ((int)dimension.getWidth() / 2);
		int y = ((int)dimension.getHeight() / 2) - 186 - userPaddle.getRadius();
		
		robot.mouseMove(x, y); //moves the mouse to that location
	}

	//this is the game loop
	public void run(){
		Recorder recorder = new Recorder(" ");
		//these ints dont need to be global because they're read in from the client each time in the loop
		int puckX = 0, puckY = 0, userPaddleX = 0, userPaddleY = 0; 
		
		//while loop runs until someone reaches 7 goals
		while(yourNumGoals < 7 && oppNumGoals < 7){

			//client sends your paddle locations
			try{ //maybe remove this try catch and surround the run loop with a try catch for SocketException e
				client.send(yourPaddleX + " " + yourPaddleY);
			}catch(Exception e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
				JOptionPane.showMessageDialog(null, "Error with Print Writer!", "ERROR", JOptionPane.ERROR_MESSAGE);
			}

			//client gets puck and user paddle locations in String form 
			String input = null;
			try{ //see comment by above try-catch
				input = client.readPositions();
			}catch(Exception e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
				JOptionPane.showMessageDialog(null, "Error with Buffered Reader!", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
			
			if(input.equals("User Goal")){ //if the client reads in that it's a user goal, call the goal method with the parameter of opponent goal
				goal(opponentGoal);
			}else if(input.equals("Opponent Goal")){ //if the client reads in that it's an opponent goal, call the goal method with the parameter of user goal
				goal(userGoal);
			}else{ //if its not a goal, parse the string for the puck x and y locations and user paddle x and y locations
				//parses and substrings to get puck x and y
				puckX = Integer.parseInt(input.substring(0, input.indexOf(" ")));
				input = input.substring(input.indexOf(" ") + 1);
				puckY = Integer.parseInt(input.substring(0, input.indexOf(" ")));
				input = input.substring(input.indexOf(" ") + 1);

				//parses and substrings to get paddle x and y
				userPaddleX = Integer.parseInt(input.substring(0, input.indexOf(" ")));
				input = input.substring(input.indexOf(" ") + 1);
				userPaddleY = Integer.parseInt(input);
			}
			
			//sets the bounds of everything
			userPaddle.setBounds(userPaddleX, userPaddleY, 50, 50);
			puck.setBounds(puckX, puckY, 30, 30);
			opponentPaddle.setBounds(yourPaddleX, yourPaddleY, 50, 50);

			//thread.sleep to keep the game at a proper speed (100 updates / second)
			try{
				Thread.sleep(10);
			}catch(InterruptedException e){}
			
			if(yourNumGoals>=5||oppNumGoals>=5)
			{
				recorder.record(puckX,puckY,userPaddleX, userPaddleY, opponentPaddle.getX(), opponentPaddle.getY());
			}
		}
		
		Playback p = new Playback(recorder.getLocations(), oppColorChoice, colorChoice);
		p.play();
		
		JOptionPane.showMessageDialog(null, "Game Over!"); //once someone scores 7, show a JOptionPane that says game over
	}
	
	//method for if someone scores a goal to make the goal flash, update scores, and reset positions of objects 
	public void goal(JLabel goal){
		
		puck.setVisible(false); //sets the puck invisible so that it looks like it went in the goal
		puck.setBounds(0, 0, 30, 30); //moves the invisible puck to the upper left corner
		Color normalColor = goal.getBackground(); //gets the original background color of the goal that was scored on (used for making it flash)
		
		//figures out who scored and adds to the persons score accordingly
		if(goal.equals(userGoal)){
			oppNumGoals++;
		}else{
			yourNumGoals++;
		}
		
		//updates the scores visually on the frame
		opponentScore.setText(client.getYourName() + ": " + oppNumGoals);
		userScore.setText(client.getOpponentName() + ": " + yourNumGoals);
		
		
		//for loop makes the goal flash between white and original color every tenth of a second
		for(int x = 0; x < 25; x++){
			if((x % 2) == 0){
				goal.setBackground(Color.white);
			}else{
				goal.setBackground(normalColor);
			}
			
			//thread.sleep implements a pause of 0.1 seconds so the flashing is slow enough to be seen
			try{
				Thread.sleep(100);
			}catch(InterruptedException e){}
		}	
		
		goal.setBackground(normalColor); //set the goal back to its original color
		
		//reset everything to original position
		puck.setVisible(true);
		puck.setBounds(222-15, 286-15, 30, 30);
		userPaddle.setBounds(222-25, 286+200-25, 50, 50);
		opponentPaddle.setBounds(222-25, 286-200-25, 50, 50);
		setMousePosition();
	}

	//mouse motion listener for making the paddle follow the mouse
	private class ML implements MouseMotionListener{

		public void mouseDragged(MouseEvent e){
			mouseEvent(e);
		}

		public void mouseMoved(MouseEvent e){
			mouseEvent(e);
		}

		public void mouseEvent(MouseEvent e){
			
			int mouseX = e.getPoint().x;
			int mouseY = e.getPoint().y;
			
			//makes sure that the paddle stays within the bounds of the game board
			if((mouseX - userPaddle.getRadius()) >= 50 && (mouseX + userPaddle.getRadius()) <= 394 && (mouseY - userPaddle.getDiameter()) >= 50 && mouseY<286){
				frame.setCursor(blankCursor);
				yourPaddleX = mouseX - userPaddle.getRadius();
				yourPaddleY = mouseY - userPaddle.getDiameter();
			}else{ //if outside the game board, paddle doesnt follow and normal mouse becomes visible
				Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
				frame.setCursor(cursor);
			}
		}
	}
}