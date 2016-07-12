package ackermanCoplanMuscianoAirHockey; //NEEDS COMMENTED BUT CURRENTLY NOT IN USE

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;

public class Playback {

	private static JFrame frame;
	private static Puck puck;
	private static Paddle userPaddle, computerPaddle;
	private static ArrayList<Integer>coords = new ArrayList<Integer>();
	private static String userColor, oppColor;
	
	public void play()
	{
			
		
		//File file = new File("U:\\GAME.txt");
		//Scanner sc = new Scanner(file);
		setUp();
		
		for(int x = coords.size()-600;x<coords.size();x+=6){
			
			int puckX = coords.get(x);
			int puckY = coords.get(x+1);
			int userPaddleX = coords.get(x+2);
			int userPaddleY = coords.get(x+3);
			int opponentPaddleX = coords.get(x+4);
			int opponentPaddleY = coords.get(x+5);
			
			puck.setBounds(puckX, puckY, 30, 30);
			userPaddle.setBounds(userPaddleX, userPaddleY, 50, 50);
			computerPaddle.setBounds(opponentPaddleX, opponentPaddleY, 50, 50);
			
			try{
				Thread.sleep(50);
			}catch(InterruptedException e){e.printStackTrace();}
		}
		
		//sc.close();
	}
	
		
		
	public Playback(ArrayList<Integer> coordinates, String UserColor, String OppColor)
	{
		for(int x = 0;x<coordinates.size();x++)
		{
			coords.add(coordinates.get(x));
		}
		userColor = UserColor;
		oppColor = OppColor;
	}
	
	public void setUp(){

		frame = new JFrame("Air Hockey");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(null);

		puck = new Puck();
		puck.setBounds(222-puck.getRadius(), 286-puck.getRadius(), 30, 30);
		ImageIcon puckIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/puck.png"));
		puck.setIcon(puckIcon);

		userPaddle = new Paddle();
		ImageIcon userPaddleIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/" + userColor.toLowerCase() + "Paddle.png"));
		userPaddle.setIcon(userPaddleIcon);
		
		userPaddle.setBounds(222-userPaddle.getRadius(), 286+200-userPaddle.getRadius(), 50 ,50);

		computerPaddle = new Paddle();
		ImageIcon opponentPaddleIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/" + oppColor.toLowerCase() + "Paddle.png"));
		computerPaddle.setIcon(opponentPaddleIcon);
		computerPaddle.setBounds(222-computerPaddle.getRadius(), 286-200-computerPaddle.getRadius(), 50, 50);

		JLabel userGoal = new JLabel();
		userGoal.setOpaque(true);
		switch(userColor)
		{
		case "Red": userGoal.setBackground(Color.red);
		break;
		case "Orange": userGoal.setBackground(new Color(234, 47, 1));
		break;
		case "Green": userGoal.setBackground(new Color(0, 154, 1));
		break;
		case "Pink": userGoal.setBackground(new Color(237, 33, 121));
		break;	
		case "Purple": userGoal.setBackground(Color.magenta);
		break;	
		case "Blue": userGoal.setBackground(Color.blue);
		}
		userGoal.setBounds(157, 522, 130, 50);

		JLabel opponentGoal = new JLabel();
		opponentGoal.setOpaque(true);
		switch(oppColor)
		{
		case "Red": opponentGoal.setBackground(Color.red);
		break;
		case "Orange": opponentGoal.setBackground(new Color(234, 47, 1));
		break;
		case "Green": opponentGoal.setBackground(new Color(0, 154, 1));
		break;
		case "Pink": opponentGoal.setBackground(new Color(237, 33, 121));
		break;	
		case "Purple": opponentGoal.setBackground(Color.magenta);
		break;	
		case "Blue": opponentGoal.setBackground(Color.blue);
		}
		opponentGoal.setBounds(157, 0, 130, 50);

		JLabel userScore = new JLabel("You: ", JLabel.CENTER);
		userScore.setOpaque(false);
		userScore.setFont(new Font("Arial Bold", Font.BOLD, 15));
		switch(userColor)
		{
		case "Red": userScore.setForeground(Color.red);
		break;
		case "Orange": userScore.setForeground(new Color(234, 47, 1));
		break;
		case "Green": userScore.setForeground(new Color(0, 154, 1));
		break;
		case "Pink": userScore.setForeground(new Color(237, 33, 121));
		break;	
		case "Purple": userScore.setForeground(Color.magenta);
		break;
		case "Blue": userScore.setForeground(Color.blue);
		break;
		}
		userScore.setBounds(15, 532, 90, 30);

		JLabel opponentScore = new JLabel("Computer: ", JLabel.CENTER);
		opponentScore.setOpaque(false);
		opponentScore.setFont(new Font("Arial Bold", Font.BOLD, 15));
		switch(oppColor)
		{
		case "Red": opponentScore.setForeground(Color.red);
		break;
		case "Orange": opponentScore.setForeground(new Color(234, 47, 1));
		break;
		case "Green": opponentScore.setForeground(new Color(0, 154, 1));
		break;
		case "Pink": opponentScore.setForeground(new Color(237, 33, 121));
		break;	
		case "Purple": opponentScore.setForeground(Color.magenta);
		break;
		case "Blue": opponentScore.setForeground(Color.blue);
		break;
		}
		opponentScore.setBounds(15, 10, 90, 30);

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

		JLabel walls = new JLabel();
		walls.setBounds(0, 0, 444, 572);
		ImageIcon wallsIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/border5.jpg"));
		walls.setIcon(wallsIcon);

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
	}
}
