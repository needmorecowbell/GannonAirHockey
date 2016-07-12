package ackermanCoplanMuscianoAirHockey; //COMMENTED

import javax.swing.JOptionPane;

public class Driver {

	public static int cpuDifficulty = 2;
	public static String hostColor = "Red", joinColor = "Blue", colorChoice = "";
	
	public static void main(String[] args){
	
		boolean run = true;
		while(run){
			
			SettingsWindow sw = new SettingsWindow();
			MainMenu mainMenu = new MainMenu();
			
			while(!mainMenu.isButtonClicked()){
				try{
					Thread.sleep(100);
				}catch(InterruptedException e){}
			}
			
			switch(mainMenu.getButton())
			{
			case '1': singlePlayer();
			run = false;
			break;
			case '2': twoPlayer();
			run = false;
			break;
			case 'S': sw.setUp();
			while(!sw.isDoneClicked()){
				try{
					Thread.sleep(100);
				}catch(InterruptedException e){}
			}
			String settings = sw.getSettings();
			int index;
			colorChoice = settings.substring(0, (index = settings.indexOf(" ")));
			int cpuValue = Integer.parseInt(settings.substring(index+1));
			switch(cpuValue)
			{
			case 0: cpuDifficulty = 2;
			break;
			case 1: cpuDifficulty = 3;
			break;
			case 2: cpuDifficulty = 4;
			break;
			case 3: cpuDifficulty = 6;
			break;
			case 4: cpuDifficulty = 8;
			break;	
			}
			break;
			default: JOptionPane.showMessageDialog(null, "Error with main menu!", "ERROR", JOptionPane.ERROR_MESSAGE);
			break;
			}
		}
		
		System.exit(0);
		
		/* FOUR PERSON AND MULTIPLAYER IN GENERAL
		 * AARON--The entire four player game needs written -- a lot of code can be copied from the two person classes
		 * AARON--Also need to catch socket exceptions for when opponent disconnects
		 * AARON--We may have to make the server implement runnable cause you cannot close the window when hosting and waiting for a connection (or we could institute a timeout)
		 */
		
		/* FEATURES TO ADD
		 * AARON--If possible, give options to change background image(put in menu)
		 * AARON--set max characters for name so its always visible
		 * play with goal values for when it is a goal (bounds of goal and determining when puck is in)
		 * make connection recyclable -- Done?
		 */
	}
	
	public static void singlePlayer(){
		if(colorChoice.equals("")){
			new ComputerGAME(hostColor, cpuDifficulty);
		}else{
			new ComputerGAME(colorChoice, cpuDifficulty);
		}
		System.exit(0);
	}
	
	public static void twoPlayer(){
		
		//sets up the two person multiplayer menu
		TwoPlayerMenu twoPlayerMenu = new TwoPlayerMenu();

		//waits until you have clicked either join or host and been connected
		while(!twoPlayerMenu.isButtonClicked()){
			//thread.sleep saves processor space
			try{
				Thread.sleep(100);
			}catch(InterruptedException e){}
		}

		//figures out which button you have pressed and acts accordingly

		switch(twoPlayerMenu.getButton())
		{
		case 'h': if(!colorChoice.equals("")){hostColor = colorChoice;} 
		new ServerGAME(twoPlayerMenu.getServer(), hostColor); //host was selected, it starts the ServerGAME using the server from within the menu class
		break;
		case 'j': if(!colorChoice.equals("")){joinColor = colorChoice;}
		new ClientGAME(twoPlayerMenu.getClient(), joinColor); //join was selected, it starts the ClientGAME code using the already connected client from within the menu class
		break;
		default: JOptionPane.showMessageDialog(null, "Error with two-player menu!", "ERROR", JOptionPane.ERROR_MESSAGE);
		break;
		}
		System.exit(0);
	}
}
