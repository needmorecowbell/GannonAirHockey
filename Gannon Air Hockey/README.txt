PROJECT NAME AND VERSION
BP Air Hockey
Adam Ackerman, Aaron Coplan, and Adam Musciano
Version 1.0.0

HOW TO RUN
Double click on the executable (BPAirHockey.exe) to run the application.

FILES INCLUDED	
The files included in the zip folder are all of the source files (.java), as well as the executable (.exe) application. In addition, all of the sprites used within 
the game have been included (.jpg or .png). Also included are the instruction file and the problem file (.txt). The list of files contained:

AI.java				Playback.java
ClientGAME.java		Puck.java
ComputerGAME.java		Recorder.java
Driver.java			ServerGAME.java	
IPConfigurer.java		SettingsWindow.java
MainMenu.java			TwoPersonClient.java
Paddle.java			TwoPersonServer.java
TwoPlayerMenu.java		border5.jpg
bluePaddle.png			greenPaddle.png
orangePaddle.png		pinkPaddle.png
puck.png			purplePaddle.png
redPaddle.png			whitebackground.png	
BPAirHockey.exe

GAMEPLAY
Our game implements a friendly and easily understood user interface, designed such that anyone could play the game with relative ease. The user controls the paddle 
using the mouse. The game has a realistic physics engine using impulse, momentum, transfer of energy, angular collisions, and friction. The user has the option to face 
the computer or another local opponent. Paddles are bounded to each player’s half. The game has smooth and consistent graphics, with a frame rate of 100 frames per second. 
The goal of the game is to score seven goals. Once the user or his or her opponent reaches seven goals, the game is over, and the user can play again.

NETWORK AND SETTINGS
Our game implements local multiplayer, where the user can play against an opponent on a separate computer on the same internet network. The program uses sockets 
and input and output streams. The two computers establish a direct connection and information is transferred in real time for live gameplay. To establish connection, one 
player must host and the other must join. There is a settings window where the user can choose his or her color as well as computer difficulty if choosing to play the AI.
