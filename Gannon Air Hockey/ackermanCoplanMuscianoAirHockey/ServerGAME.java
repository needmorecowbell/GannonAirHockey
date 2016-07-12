package ackermanCoplanMuscianoAirHockey; //NEEDS COMMENTED

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Point;
import java.awt.Font;
import java.awt.Robot;
import java.awt.Cursor;
import java.awt.AWTException;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JOptionPane;

//true width is 444, true height is 572
public class ServerGAME {

	private String colorChoice, oppColorChoice;
	private JFrame frame;
	private Puck puck;
	private JLabel userGoal, opponentGoal, userScore, opponentScore;
	private Paddle opponentPaddle, userPaddle;
	
	private double puckSpeed, puckVX, puckVY, puckDIRX, puckDIRY;
	private int puckX, puckY;
	
	private int userPaddleX, userPaddleY, opponentPaddleX, opponentPaddleY;
	private int yourNumGoals = 0, oppNumGoals = 0;
	
	private int userPaddleSpeed = 0, opponentPaddleSpeed = 0;
	private Point opponentPreviousPoint = new Point(0, 0);
	private Cursor blankCursor;
	
	private Robot robot;
	private TwoPersonServer server;
	
	private final double FRICTION = 1.0045, ENERGY_TRANSFER = 1.00023;
	private final int PADDLE_DIAMETER = 50;
	
	public ServerGAME(TwoPersonServer server, String colorChoice){
		
		this.colorChoice = colorChoice;
		this.server = server;
		oppColorChoice = server.getOpponentColor(colorChoice);
		setUp();
	}
	
	public void setUp(){
		
		puckSpeed = 0;
		puckDIRX = 1;
		puckDIRY = 1;
		
		try{
			this.robot = new Robot();
		}catch(AWTException e){}
		
		frame = new JFrame("Air Hockey");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.addMouseMotionListener(new ML());
		
		BufferedImage cursorImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		this.blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		frame.setCursor(blankCursor);
		
		puck = new Puck();
		puck.setBounds(222-puck.getRadius(), 286-puck.getRadius(), 30, 30);
		ImageIcon puckIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/puck.png"));
		puck.setIcon(puckIcon);
		
		userPaddle = new Paddle();
		ImageIcon userPaddleIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/" + colorChoice.toLowerCase() + "Paddle.png"));
		userPaddle.setIcon(userPaddleIcon);
		userPaddle.setBounds(222-userPaddle.getRadius(), 286+200-userPaddle.getRadius(), PADDLE_DIAMETER, PADDLE_DIAMETER);
		
		opponentPaddle = new Paddle();
		ImageIcon opponentPaddleIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/" + server.getOppColorChoice().toLowerCase() + "Paddle.png"));
		opponentPaddle.setIcon(opponentPaddleIcon);
		opponentPaddle.setBounds(222-opponentPaddle.getRadius(), 286-200-opponentPaddle.getRadius(), PADDLE_DIAMETER, PADDLE_DIAMETER);
		
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
		
		opponentGoal = new JLabel();
		opponentGoal.setOpaque(true);
		oppColorChoice = server.getOppColorChoice();
		switch(oppColorChoice)
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
		}
		opponentGoal.setBounds(157, 0, 130, 50);
		
		userScore = new JLabel(server.getYourName() + ": " + yourNumGoals, JLabel.CENTER);
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
		
		opponentScore = new JLabel(server.getOpponentName() + ": " + oppNumGoals, JLabel.CENTER);
		opponentScore.setOpaque(false);
		opponentScore.setFont(new Font("Arial Bold", Font.BOLD, 15));
		switch(oppColorChoice)
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
		}
		opponentScore.setBounds(15, 10, 90, 30);
		
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
		
		JLabel walls = new JLabel();
		walls.setBounds(0, 0, 444, 572);
		ImageIcon wallsIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/border5.jpg"));
		walls.setIcon(wallsIcon);
		
		frame.add(puck);
		frame.add(userPaddle);
		frame.add(opponentPaddle);
		frame.add(userGoal);
		frame.add(opponentGoal);
		frame.add(userScore);
		frame.add(opponentScore);
		frame.add(midline);
		frame.add(back);
		frame.add(walls);
		frame.setSize(450, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		setMousePosition();
		run();
	}
	
	public void setMousePosition(){
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();//getBounds?
		int x = ((int)dimension.getWidth() / 2);
		int y = ((int)dimension.getHeight() / 2) + 186 - userPaddle.getRadius();
		
		robot.mouseMove(x, y);
	}
	
	public void run(){
		Recorder recorder = new Recorder(" ");
		while(yourNumGoals < 7 && oppNumGoals < 7){
			
			//read data
			String input = server.readPositions();
			
			//check if goal and print accordingly
			if(checkIfUserGoal()){
				server.send("User Goal");
				goal(opponentGoal);
			}else if(checkIfOpponentGoal()){
				server.send("Opponent Goal");
				goal(userGoal);
			}else{
				try{
					server.send(puckX + " " + puckY + " " + userPaddleX + " " + userPaddleY);
				}catch(Exception e){
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(null, "Error with Print Writer!", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			opponentPaddleX = Integer.parseInt(input.substring(0, input.indexOf(" ")));
			opponentPaddleY = Integer.parseInt(input.substring(input.indexOf(" ") + 1));
			
			calculateOpponentPaddleSpeed();
			
			puckX = puck.getX();
			puckY = puck.getY();
			
			wallCollision();
			puckMove();
			wallCollision();
			
			puck.setBounds(puckX, puckY, 30, 30);
			userPaddle.setBounds(userPaddleX, userPaddleY, PADDLE_DIAMETER, PADDLE_DIAMETER);
			opponentPaddle.setBounds(opponentPaddleX, opponentPaddleY, PADDLE_DIAMETER, PADDLE_DIAMETER);
			
			friction();
			
			try{
				Thread.sleep(10);
			}catch(InterruptedException e){}
			
			if(yourNumGoals>=5||oppNumGoals>=5)
			{
				recorder.record(puckX,puckY,userPaddleX, userPaddleY, opponentPaddle.getX(), opponentPaddle.getY());
			}
		}
		
		Playback p = new Playback(recorder.getLocations(), colorChoice, oppColorChoice);
		p.play();
		
		JOptionPane.showMessageDialog(null, "Game Over!");
	}
	
	public void calculateOpponentPaddleSpeed(){
		
		int opponentPaddleCX  = opponentPaddleX + opponentPaddle.getRadius();
		int opponentPaddleCY = opponentPaddleY + opponentPaddle.getRadius();
		Point currentPoint = new Point(opponentPaddleCX, opponentPaddleCY);
		
		opponentPaddleSpeed = (int)((currentPoint.distance(opponentPreviousPoint)) / ENERGY_TRANSFER);
		opponentPreviousPoint = currentPoint;
		
	}
	
	public void friction(){
		
		puckSpeed /= FRICTION;
	}
	
	public void goal(JLabel goal){
		puck.setVisible(false);
		puck.setBounds(0, 0, 30, 30);
		Color normalColor = goal.getBackground();
		
		if(goal.equals(userGoal)){
			oppNumGoals++;
		}else{
			yourNumGoals++;
		}
		opponentScore.setText(server.getOpponentName() + ": " + oppNumGoals);
		userScore.setText(server.getYourName() + ": " + yourNumGoals);
		
		for(int x = 0; x < 25; x++){
			if((x % 2)== 0){
				goal.setBackground(Color.white);
			}else{
				goal.setBackground(normalColor);
			}
			
			try{
				Thread.sleep(100);
			}catch(InterruptedException e){}
		}
		goal.setBackground(normalColor);
		
		//reset everything to original positions
		puck.setVisible(true);
		puck.setBounds(222-15, 286-15, 30, 30);
		userPaddle.setBounds(222-25, 286+200-25, 50 ,50);
		opponentPaddle.setBounds(222-25, 286-200-25, 50, 50);
		puckSpeed = 0;
		setMousePosition();
	}
	
	public boolean checkIfUserGoal(){
		
		if(puck.getY() <= 50){
			if((puck.getCX() - puck.getRadius()) >= 157 && (puck.getCX() + puck.getRadius()) <= 287){
				return true;
			}
		}
			
		return false;
	}
	
	public boolean checkIfOpponentGoal(){
		
		if((puck.getY() + puck.getDiameter()) >= 521){
			if((puck.getCX() - puck.getRadius()) >= 157 && (puck.getCX() + puck.getRadius() <= 287)){
				return true;
			}
		}
		
		return false;
		
	}
	
	public void puckStep(){
		
		puckVX = puckSpeed*puckDIRX;
		puckVY = puckSpeed*puckDIRY;
		
		puckX += (int)puckVX;
		puckY += (int)puckVY;
		
		wallCollision();
	}
	
	public void puckMove(){
		
		puckStep();
		if(userPaddleCollision()){
			
			puckDIRX = (puck.getCX() - userPaddle.getCX()) / (Math.sqrt(((puck.getCX() - userPaddle.getCX()) * (puck.getCX() - userPaddle.getCX())) + ((puck.getCY() - userPaddle.getCY()) * (puck.getCY() - userPaddle.getCY()))));
			puckDIRY = (puck.getCY() - userPaddle.getCY()) / (Math.sqrt(((puck.getCX() - userPaddle.getCX()) * (puck.getCX() - userPaddle.getCX())) + ((puck.getCY() - userPaddle.getCY()) * (puck.getCY() - userPaddle.getCY()))));
			if(userPaddleSpeed < 4){
				puckSpeed += userPaddleSpeed;
			}else if(userPaddleSpeed <= 13){//could change 15
				puckSpeed = userPaddleSpeed;
			}else{
				puckSpeed = 13;
			}
			puckStep();
		}else if(opponentPaddleCollision()){
			
			puckDIRX = (puck.getCX() - opponentPaddle.getCX()) / (Math.sqrt(((puck.getCX() - opponentPaddle.getCX()) * (puck.getCX() - opponentPaddle.getCX())) + ((puck.getCY() - opponentPaddle.getCY()) * (puck.getCY() - opponentPaddle.getCY()))));
			puckDIRY = (puck.getCY() - opponentPaddle.getCY()) / (Math.sqrt(((puck.getCX() - opponentPaddle.getCX()) * (puck.getCX() - opponentPaddle.getCX())) + ((puck.getCY() - opponentPaddle.getCY()) * (puck.getCY() - opponentPaddle.getCY()))));
			if(opponentPaddleSpeed < 4){
				puckSpeed += opponentPaddleSpeed;
			}else if(userPaddleSpeed <= 13){
				puckSpeed = opponentPaddleSpeed;
			}else{
				puckSpeed = 13;
			}
			puckStep();
		}
		
	}
	
	
	public boolean userPaddleCollision(){
		
		double distance = Math.sqrt((puck.getCX() - userPaddle.getCX()) * (puck.getCX() - userPaddle.getCX()) + ((puck.getCY() - userPaddle.getCY()) * (puck.getCY() - userPaddle.getCY())));
		if(distance <= (puck.getRadius() + userPaddle.getRadius())){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean opponentPaddleCollision(){
		
		double distance = Math.sqrt((puck.getCX() - opponentPaddle.getCX()) * (puck.getCX() - opponentPaddle.getCX()) + ((puck.getCY() - opponentPaddle.getCY()) * (puck.getCY() - opponentPaddle.getCY())));
		if(distance <= (puck.getRadius() + opponentPaddle.getRadius())){
			return true;
		}else{
			return false;
		}
	}
	
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
		}else if(puck.getX() <= 50 /*left wall border*/){
			puckDIRX = Math.abs(puckDIRX);
		}else if((puck.getX() + puck.getDiameter()) >= 394 /*right wall border*/){
			puckDIRX = -Math.abs(puckDIRX);
		}else if(puck.getY() <= 50 /*top wall border*/){
			puckDIRY = Math.abs(puckDIRY);
		}else if((puck.getY() + puck.getDiameter()) >= 522 /*bottom wall border*/){
			puckDIRY = -Math.abs(puckDIRY);
		}
	}
	
	private class ML implements MouseMotionListener{

		private Point userPreviousPoint = new Point(0, 0);
		//DO THE SAME THING FOR BOTH DRAGGED AND MOVED
		public void mouseDragged(MouseEvent e) {
			mouseEvent(e);
		}
		
		public void mouseMoved(MouseEvent e) {
			mouseEvent(e);
		}
		
		public void mouseEvent(MouseEvent e){
			
			int mouseX = e.getPoint().x;
			int mouseY = e.getPoint().y;
			
			if((mouseX - userPaddle.getRadius()) >= 50 && (mouseX + userPaddle.getRadius()) <= 394 && (mouseY - userPaddle.getDiameter()) >= 50 && ((mouseY) >= 332 && (mouseY)<=522)){
				frame.setCursor(blankCursor);
				userPaddleX = mouseX - userPaddle.getRadius();
				userPaddleY = mouseY - userPaddle.getDiameter();
			}else{
				Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
				frame.setCursor(cursor);
			}
			
			userPaddleSpeed = (int)((e.getPoint().distance(userPreviousPoint)) / ENERGY_TRANSFER);
			
			userPreviousPoint = e.getPoint();
		}
	}
}
