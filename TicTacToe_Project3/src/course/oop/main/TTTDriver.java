package course.oop.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;
import course.oop.controller.*;

public class TTTDriver {
	public static int interval;
	public static int timeDuration = 0;
	public static Timer timer;
	public static boolean isCounting = false;
	
	public static void main(String[] args) {
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			boolean isRunning = true;
			String input = "";
			int intVal = 0;
			String currentPlayerTurn = "";
			int numPlayers = 0;
			String p1Username = "";
			String p2Username = "computer";
			String p1marker = "X";
			String p2marker = "O";
			
			int delay = 1000;
			int period = 1000;
			
			int rowInput = -1;
			int colInput = -1;
			
			//Prompt User for 2 Player or Player vs AI
			System.out.print("How many players will there be? (1 or 2): ");
			input = br.readLine();
			numPlayers = Integer.parseInt(input);
			
			//Prompt User(s) to input username and marker preference.
			System.out.print("Enter Player 1 Username: ");
			p1Username = br.readLine();
			System.out.print("Enter Player 1 marker preference: ");
			p1marker = br.readLine();
			if(numPlayers == 2)
			{
				System.out.print("Enter Player 2 Username: ");
				p2Username = br.readLine();
				System.out.print("Enter Player 2 marker preference: ");
				p2marker = br.readLine();
			}
			
			//Prompt User for timer
			System.out.print("Timer for each player's turn: ");
			input = br.readLine();
			interval = Integer.parseInt(input);
			timeDuration = interval;
			
			//Create interface object
			TTTControllerImpl interfaceA = new TTTControllerImpl();
			
			while(isRunning)
			{
				//Display Main menu
				displayMainMenu();
				//Get User Input
				input = br.readLine();
				intVal = Integer.parseInt(input);
				
				boolean isGameInitialized = false;
				
				if(intVal == 1)
				{
					//If Game object isn't initialized, do so
					if(!isGameInitialized) {
						interfaceA.startNewGame(numPlayers, timeDuration);
						interfaceA.createPlayer(p1Username, p1marker, 1);
						if(numPlayers == 2)
							interfaceA.createPlayer(p2Username, p2marker, 2);
						isGameInitialized = true;
					}
					//While loop using TTTGame.isGameOver variable
					boolean isGameOver = interfaceA.getGame().getGameOver();
					while(!isGameOver)
					{
						//Set up new timer
						interval = timeDuration;
						if(interfaceA.getGame().getDoesTimerMatter()) {
							timer = new Timer();
							timer.schedule(new TimerTask() {
								public void run() {
									System.out.println("Sec(s) left: " + setInterval());
								}
							}, delay, period );
						}
						
						
						//Start of turn
						isCounting = true;
						
						while(isCounting) {
							//Get Current Player turn
							if(interfaceA.getGame().getCurrentTurn())
								currentPlayerTurn = p1Username;
							else
								currentPlayerTurn = p2Username;
							
							//Display Game Menu
							displayGameMenu(currentPlayerTurn);
							
							//Get User Input
							input = br.readLine();
							intVal = Integer.parseInt(input);
							//Evaluate Input
							switch(intVal) {
								case 1:
									//Determine if there is a winner
									if(interfaceA.determineWinner() != 0) {
										isGameOver = true;
										isGameInitialized = false;
										//Output to Screen
										System.out.print("Game Over! ");
										if(interfaceA.determineWinner() == 1)
											System.out.println(p1Username + " wins!\n");
										else if(interfaceA.determineWinner() == 2)
											System.out.println(p2Username + " wins!\n");
										else
											System.out.println("Both players tied!\n");
									}else {
										
										//Determine Current Player #
										int currentPlayer;
										if(interfaceA.getGame().getCurrentTurn())
											currentPlayer = 1;
										else
											currentPlayer = 2;
										
										if(numPlayers == 1 && currentPlayer == 2) {
											//Make the Ai take its turn
											interfaceA.getGame().makeMove();
										}
										else {
											//Get Selection inputs
											System.out.print("row: ");
											input = br.readLine();
											rowInput = Integer.parseInt(input);
											System.out.print("col: ");
											input = br.readLine();
											colInput = Integer.parseInt(input);
											
											//Check if player inputed value in time
											if(isCounting)
											{
												boolean isChoiceValid = interfaceA.setSelection(rowInput, colInput, currentPlayer);
												//Print out message depending on output: Success/Failure
												if(isChoiceValid) 
												{
													System.out.println("Selection is Valid!\n");
												}
												else
													System.out.println("Selection Invalid! Try Again!\n");
											}
											else 
											{
												System.out.println("Time out! Random move made!");
												interfaceA.getGame().randomizeMove();
												timer.cancel();
											}
										}
									}	
									break;
								case 2:
									//Call interface function to return string;
									//Print out display
									System.out.println(interfaceA.getGameDisplay() + "\n");
									break;
								case 3:
									//Quit Game
									isGameOver = true;
									break;
								default:
									//Error
									System.out.println("Error! Invalid menu choice. Please try again.");
									break;
							}
						}
						
					}
					
				}else if (intVal == 2)
				{
					boolean inMenu = true;
					while(inMenu) {
						//Display Player Details & Timer
						System.out.println(	"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
											"~~~~~~~~~~~~~  Player Details   ~~~~~~~~~~~~~\n" +
											"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" + 
											"(0) Player 1 Username: " + p1Username + "\n(1) Player 1 marker: " + p1marker
											+ "\n(2) Player 2 Username: " + p2Username + "\n(3) Player 2 marker: " + p2marker
											+ "\n(4) Timer in secs: " + timeDuration + "\n(5) Number of Players: " + numPlayers + "\n(6) Quit\n" );
						System.out.print("Select an option to edit: ");
						input = br.readLine();
						intVal = Integer.parseInt(input);
						
						if(intVal == 6) {
							inMenu = false;
						}
						else {
							switch(intVal) {
								case 0:
									System.out.print("Old value: " );
									System.out.println(p1Username);
									System.out.print("New value: ");
									p1Username = br.readLine();
									break;
								case 1:
									System.out.print("Old value: " );
									System.out.println(p1marker);
									System.out.print("New value: ");
									p1marker = br.readLine();
									break;
								case 2:
									System.out.print("Old value: " );
									System.out.println(p2Username);
									System.out.print("New value: ");
									p2Username = br.readLine();
									break;
								case 3:
									System.out.print("Old value: " );
									System.out.println(p2marker);
									System.out.print("New value: ");
									p2marker = br.readLine();
									break;
								case 4:
									System.out.print("Old value: " );
									System.out.println(timer);
									System.out.print("New value: ");
									input = br.readLine();
									timeDuration = Integer.parseInt(input);
									interval = timeDuration;
									break;
								case 5:
									System.out.print("Old value: " );
									System.out.println(numPlayers);
									System.out.print("New value: ");
									input = br.readLine();
									numPlayers = Integer.parseInt(input);
									break;
								default:
									//Error
									System.out.println("Error! Invalid menu choice. Please try again.");
									break;
							}
						}
					}
				}else if (intVal == 3) {
					//Quit
					System.out.println("Quitting Program...");
					isRunning = false;
				}else
				{
					//Error
					System.out.println("Error! Invalid menu choice. Please try again.");
					displayMainMenu();
				}
			}
			
		} catch (IOException e) {
            e.printStackTrace();
        } 
	}
	
	public static void displayMainMenu()
	{
		String menu = 	"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
						"~~~~~~~~~~~~~    Tic Tac Toe    ~~~~~~~~~~~~~\n" +
						"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" + 
						"(Press a number to choose a menu option)\n\n" + 
						"1: New Game/Play Again\n" + 
						"2: Edit Options\n" +
						"3: Quit\n";
		System.out.println(menu);
	}
	
	public static void displayGameMenu(String playerUsername)
	{
		String menu =  	"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
						"~~~~~~~~~~~~~     Game Menu     ~~~~~~~~~~~~~\n" +
						"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" + 
						"(Press a number to choose a menu option)\n\n" + 
						"1: Make Selection ("+ playerUsername +")\n" + 
						"2: Display Game Board\n" +
						"3: Quit\n";
		System.out.println(menu);
	}
	
	public static final int setInterval() {
		if(interval <= 1) {
			isCounting  = false;
			timer.cancel();
		}
			
		return --interval;
	}
}