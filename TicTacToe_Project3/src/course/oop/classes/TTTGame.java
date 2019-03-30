package course.oop.classes;

import java.util.Random;

public class TTTGame 
{
	private TTTPlayer player_1;
	private TTTPlayer player_2;
	private int[][] gameBoard;
	private boolean isGameOver;
	private boolean currentTurn;	//True is player 1, false is player 2
	private int turnTimer;
	private boolean doesTimerMatter;
	
	public TTTGame() 
	{
		player_1 = new TTTPlayer(1);
		player_2 = new TTTPlayer(2);
		gameBoard = new int[3][3];
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				gameBoard[i][j] = 0;
		isGameOver = false;
		currentTurn = true;
		turnTimer = 0;
		doesTimerMatter = false;
	}
	
	public TTTGame(String p1Name, String p1Marker, int timer) {
		player_1 = new TTTPlayer(p1Name, p1Marker, 0, 0);
		player_2 = new TTTAI();
		gameBoard = new int[3][3];
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				gameBoard[i][j] = 0;
		isGameOver = false;
		currentTurn = true;

		if(timer<=0) {
			turnTimer = 0;
			doesTimerMatter = false;
		}
		else {
			turnTimer = timer;
			doesTimerMatter = true;
		}
	}
	
	public TTTGame(String p1Name, String p1Marker, String p2Name, String p2Marker, int timer) {
		player_1 = new TTTPlayer(p1Name, p1Marker, 0, 0);
		player_2 = new TTTPlayer(p2Name, p2Marker, 0, 0);
		gameBoard = new int[3][3];
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				gameBoard[i][j] = 0;
		isGameOver = false;
		currentTurn = true;
		
		if(timer<=0) {
			turnTimer = 0;
			doesTimerMatter = false;
		}
		else {
			turnTimer = timer;
			doesTimerMatter = true;
		}
	}
	
	public void resetBoard() {
		gameBoard = new int[3][3];
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				gameBoard[i][j] = 0;
		isGameOver = false;
		currentTurn = true;
	}
	
	public int[][] getBoard(){
		return gameBoard;
	}
	
	public TTTPlayer getPlayer(int playerNum) {
		if(playerNum == 1)
			return player_1;
		else
			return player_2;
	}

	//Added method for use in TTTControllerInterface
	public void setPlayer(TTTPlayer newPlayer, int pNum) {
		if(pNum == 1) 
			player_1 = newPlayer;
		else if(pNum == 2)
			player_2 = newPlayer;
	}
	
	public boolean getGameOver() {
		return isGameOver;
	}
	
	public boolean getCurrentTurn() {
		return currentTurn;
	}
	
	//Set currentTurn
	public void makeNextMove(int row, int col, int playerNum) {
		if(playerNum ==1)
			gameBoard[row][col] = 1;
		else
			gameBoard[row][col] = 2;
		currentTurn = !currentTurn;
	}
	
	public int getTurnTimer() {
		return turnTimer;
	}
	
	public boolean getDoesTimerMatter() {
		return doesTimerMatter;
	}
	
	//Only human players should have to call this method during timeout
	public void randomizeMove() {
		boolean spotFound = false;
		Random rand = new Random();
		int i = 0; 
		int j = 0;
		while(!spotFound) {
			i = rand.nextInt(3);
			j = rand.nextInt(3);
			if(gameBoard[i][j] == 0) {
				spotFound = true;
			}	
		}
		if(currentTurn)
			gameBoard[i][j] = 1;
		else
			gameBoard[i][j] = 2;
		
		currentTurn = !currentTurn;
	}
	
	public int checkWinner() {
		int winner = 0;
		boolean notZero = true;
		int sum = 0;
		//Check Rows
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				sum += gameBoard[i][j];
				if(gameBoard[i][j] == 0)
					notZero = false;
			}
			//There were no zeroes, check if equal to 3 or 6.
			if(notZero) {
				if(sum == 3) {
					isGameOver = true;
					return 1;
				}
				if(sum == 6) {
					isGameOver = true;
					return 2;
				}
			}
			sum = 0; notZero = true;
		}
		
		sum = 0;
		//Check Cols
		for(int j=0;j<3;j++) {
			for(int i=0;i<3;i++) {
				sum += gameBoard[i][j];
				if(gameBoard[i][j] == 0)
					notZero = false;
			}
			//There were no zeroes, check if equal to 3 or 6.
			if(notZero) {
				if(sum == 3) {
					isGameOver = true;
					return 1;
				}
				if(sum == 6) {
					isGameOver = true;
					return 2;
				}
			}
			sum = 0; notZero = true;
		}
		
		sum = 0;
		//Check Diagonal
		for(int i=0;i<3;i++) {
			sum += gameBoard[i][i];
			if(gameBoard[i][i] == 0)
				notZero = false;
		}
		if(notZero) {
			if(sum == 3) {
				isGameOver = true;
				return 1;
			}
			if(sum == 6) {
				isGameOver = true;
				return 2;
			}
		}
		
		sum = 0; notZero = true;
		//Check AntiDiagonal
		for(int i=0;i<3;i++) {
			sum += gameBoard[i][2-i];
			if(gameBoard[i][2-i] == 0)
				notZero = false;
		}
		if(notZero) {
			if(sum == 3) {
				isGameOver = true;
				return 1;
			}
			if(sum == 6) {
				isGameOver = true;
				return 2;
			}
		}
		
		//Check if board completely filled
		notZero = true;
		for(int i=0;i<3;i++) {
			for(int j = 0;j<3;j++)
				if(gameBoard[i][j] == 0)
					notZero = false;
		}
		if(notZero) {
			winner = 3;
			isGameOver = true;	//All spots filled but no winner, thus a tie.
		}
		
		return winner;
	}
	
	//For Ai move only
	public void makeMove() {
		//Find first available spot and make its move there.
		boolean spotFound = false;
		int i = 0;
		int j = 0;
		while(i < 3 && !spotFound) {
			while(j < 3 && !spotFound) {
				if(gameBoard[i][j] == 0) {
					gameBoard[i][j] = 2;
					spotFound = true;
				}
				j++;
			}
			i++;
		}
		currentTurn = !currentTurn;
	}
}