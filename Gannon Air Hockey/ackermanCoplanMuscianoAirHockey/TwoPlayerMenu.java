package ackermanCoplanMuscianoAirHockey; //COMMENTED

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TwoPlayerMenu {

	private IPConfigurer configurer;
	private JFrame menuFrame;
	private JButton host, join;
	private String[] IPs;
	private Font f;
	private boolean buttonClicked = false;
	private char button = '-';
	private TwoPersonServer server;
	private TwoPersonClient client;
	
	public TwoPlayerMenu(){
		
		this.f = new Font("High Tower Text", Font.PLAIN, 66); //creates the font to be used on the buttons
		startIPConfiguration(); //gets all the available IPs on the network to connect to
		setUpMenu(); //sets up hte menu and makes it visible
	}
	
	public void setUpMenu(){
		
		//creates a new ActionListener to be added to the buttons
		AL actionListener = new AL();
		
		//sets up basic JFrame properties
		menuFrame = new JFrame("Menu");
		menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuFrame.setResizable(false);
		menuFrame.setLayout(new GridLayout(2, 0));
		
		//instantiates the host button with necessary properties
		host = new JButton("Host Game");
		host.setBackground(Color.cyan);
		host.setFont(f);
		host.addActionListener(actionListener); //adds the action listener
		
		//instantiates the join button with necessary properties
		join = new JButton("Join Game");
		join.setBackground(Color.pink);
		join.setFont(f);
		join.addActionListener(actionListener); //adds the action listener
		
		//adds the components, sets the size and location, and displays the JFrame
		menuFrame.add(host);
		menuFrame.add(join);
		menuFrame.setSize(600, 400);
		menuFrame.setLocationRelativeTo(null);
		menuFrame.setVisible(true);
		
		//the String[] of IPs is obtained from the finished configurer class
		this.IPs = configurer.getIPs();
		
		//now it listens until a button is clicked
	}
	
	public boolean isButtonClicked(){return buttonClicked;}
	public char getButton(){return button;}
	public TwoPersonServer getServer(){return this.server;}
	public TwoPersonClient getClient(){return this.client;}
	
	public void startIPConfiguration(){
		//instantiates the pinger then generates the necessary ips
		this.configurer = new IPConfigurer();
		configurer.configure();
	}
	
	//action listener for the host and join buttons
	private class AL implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			if(e.getSource().equals(host)){ //if they click host, do the following
				
				String yourName = (String)JOptionPane.showInputDialog(null, "Enter your name (9 characters max):", "Name", JOptionPane.QUESTION_MESSAGE);
				if(yourName.length() > 9){yourName = yourName.substring(0, 9);}
				server = new TwoPersonServer(yourName);
				
				//change to connecting screen
				menuFrame.setTitle("Connecting...");
				host.setText("Connecting. . .");
				join.setText("Searching for opponent. . .");
				join.setFont(new Font("High Tower Text", Font.PLAIN, 44));
				host.setEnabled(false);
				join.setEnabled(false);
				menuFrame.update(menuFrame.getGraphics());
				
				//server waits for connection
				server.connect();
				
				//display the opponents name that you are connected to
				menuFrame.setTitle("Connected!");
				host.setText("Opponent Found!");
				join.setText("Opponent Name: " + server.getOpponentName());
				menuFrame.update(menuFrame.getGraphics());
				
				//wait 5 seconds so you can see who you are connected to
				try{
					Thread.sleep(5000);
				}catch(InterruptedException interrupted){}
				
				//sets the menu invisible
				menuFrame.setVisible(false);
				buttonClicked = true;
				button = 'h';
				
			}else if(e.getSource().equals(join)){ //if they click join, do the following
				
				String yourName = (String)JOptionPane.showInputDialog(null, "Enter your name (9 characters max):", "Name", JOptionPane.QUESTION_MESSAGE);
				if(yourName.length() > 9){yourName = yourName.substring(0, 9);}
				client = new TwoPersonClient(IPs, yourName);
				
				//change to connecting screen
				menuFrame.setTitle("Connecting...");
				host.setText("Connecting. . .");
				join.setText("Searching for opponent. . .");
				join.setFont(new Font("High Tower Text", Font.PLAIN, 44));
				host.setEnabled(false);
				join.setEnabled(false);
				menuFrame.update(menuFrame.getGraphics());
				
				//client connects to server
				client.connect();
				
				//if it successfully connects, display the connected screen with opponent info
				//then waits 5 seconds, closes, the menu, and the driver class starts the game
				if(client.isConnected()){
					
					//display the opponents name that you are connected to
					menuFrame.setTitle("Connected!");
					host.setText("Opponent Found!");
					join.setText("Opponent Name: " + client.getOpponentName());
					menuFrame.update(menuFrame.getGraphics());

					//wait 5 seconds so you can see who you are connected to
					try{
						Thread.sleep(5000);
					}catch(InterruptedException interrupted){}

					//sets the menu invisible
					menuFrame.setVisible(false);
					buttonClicked = true;
					button = 'j';
				
				//if it fails to successfully connect, it displays that no connection was found	
				}else{
					menuFrame.setTitle("Failed to Find Connection...");
					host.setText("No Opponent Found!");
					host.setFont(new Font("High Tower Text", Font.PLAIN, 44));
					join.setText("Make sure an opponent is connected and try again!");
					join.setFont(new Font("High Tower Text", Font.PLAIN, 25));
					menuFrame.update(menuFrame.getGraphics());
				}
			}
		}
	}
}
