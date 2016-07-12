package ackermanCoplanMuscianoAirHockey; //COMMENTED

import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import javax.swing.JOptionPane;

public class TwoPersonServer {

	private ServerSocket ss;
	private Socket clientSocket;
	private PrintWriter pw;
	private BufferedReader br;
	private String yourName, opponentName, oppColorChoice;
	
	//constructor that takes in your name so it can be passed to the opponent
	public TwoPersonServer(String yourName){
		this.yourName = yourName;
	}
	
	//method to accept a connection from a client
	public void connect(){
		
		try{
			//opens the server, accepts a client, opens PrintWriter and BufferedReader
			//then sends your name and receives opponent name
			ss = new ServerSocket(63400);
			clientSocket = ss.accept();
			pw = new PrintWriter(clientSocket.getOutputStream(), true);
			br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			pw.println(yourName);
			opponentName = br.readLine();
			
		}catch(IOException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			JOptionPane.showMessageDialog(null, "Error with server connection!", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//methods for the game class to get the names of both players 
	//names are displayed alongside the scores
	public String getYourName(){return yourName;}
	public String getOpponentName(){return opponentName;}
	public String getOppColorChoice(){return oppColorChoice;}
	
	//method for sending positions of components to client in string form
	public void send(String input){pw.println(input);}
	
	//method for reading positions of components from client in string form
	public String readPositions(){
		
		try{
			return br.readLine();
		}catch(IOException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			JOptionPane.showMessageDialog(null, "Error with Buffered Reader!", "ERROR", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	public String getOpponentColor(String yourColor){
		try{
			pw.println(yourColor);
			return (oppColorChoice = br.readLine());
		}catch(IOException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			JOptionPane.showMessageDialog(null, "Error with PW or BR!", "ERROR", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
}
