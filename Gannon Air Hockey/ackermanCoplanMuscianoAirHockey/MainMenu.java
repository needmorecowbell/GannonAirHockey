package ackermanCoplanMuscianoAirHockey;

import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainMenu {

	private Font f;
	private JFrame menuFrame;
	private JButton singlePlayer, twoPlayer, settings;
	private boolean buttonClicked = false;
	private char button = '-';
	
	public MainMenu(){
		
		this.f = new Font("High Tower Text", Font.PLAIN, 66);
		setUp();
	}
	
	public void setUp(){
		
		AL actionListener = new AL();
		
		menuFrame = new JFrame();
		menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuFrame.setResizable(false);
		menuFrame.setLayout(new GridLayout(3, 0));
		
		singlePlayer = new JButton("Single Player");
		singlePlayer.setBackground(Color.cyan);
		singlePlayer.setFont(f);
		singlePlayer.addActionListener(actionListener);
		
		twoPlayer = new JButton("Local Two Player");
		twoPlayer.setBackground(Color.pink);
		twoPlayer.setFont(f);
		twoPlayer.addActionListener(actionListener);
		
		settings = new JButton("Settings");
		settings.setBackground(Color.cyan);
		settings.setFont(f);
		settings.addActionListener(actionListener);
		
		menuFrame.add(singlePlayer);
		menuFrame.add(twoPlayer);
		menuFrame.add(settings);
		menuFrame.setSize(600, 400);
		menuFrame.setLocationRelativeTo(null);
		menuFrame.setVisible(true);
	}
	
	public char getButton(){return button;}
	public boolean isButtonClicked(){return buttonClicked;}
	public void reset(){
		button = '-';
		buttonClicked = false;
		menuFrame.setVisible(true);
	}
	
	private class AL implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			if(e.getSource().equals(singlePlayer)){
				menuFrame.setVisible(false);
				buttonClicked = true;
				button = '1';
			}else if(e.getSource().equals(twoPlayer)){
				menuFrame.setVisible(false);
				buttonClicked = true;
				button = '2';
			}else if(e.getSource().equals(settings)){
				menuFrame.setVisible(false);
				buttonClicked = true;
				button = 'S';
			}
		}
	}
}
