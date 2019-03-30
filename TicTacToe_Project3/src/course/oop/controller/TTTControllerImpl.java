package course.oop.controller;

import course.oop.classes.*;

public class TTTControllerImpl implements TTTControllerInterface{
	public TTTGame game;
	
	public TTTControllerImpl()
	{
		game = new TTTGame();
	}
	
	public void startNewGame(int numPlayers, int timeoutInSecs) 
	{
		//Placeholder Values, createPlayer will replace default values with user input
		if(numPlayers > 1)
			game = new TTTGame("Player 1", "X", "Player 2", "O", timeoutInSecs);
		else
			game = new TTTGame("Player 1", "X", timeoutInSecs);
		
		game.resetBoard();
	}
	
	public void createPlayer(String username, String marker, int playerNum) 
	{
		TTTPlayer player = new TTTPlayer(username, marker, 0, 0);
		game.setPlayer(player, playerNum);
	}
	
	public boolean setSelection(int row, int col, int currentPlayer)
	{
		boolean valid= false; // Are parameters valid?
		//Code to check location
		if(game.getBoard()[row][col] == 0 && row < 3 && row >= 0 && col < 3 && col >= 0) {
			valid = true;
			game.makeNextMove(row, col, currentPlayer);
		}
		return valid;
	}
	
	public int determineWinner() 
	{
		int winner = 0; //Default winner 0
						// 0=no winner / game in progress / not all spaces have been selected; 
		 				// 1=player1; 
		 				// 2=player2; 
		 				// 3=tie/no more available locations
		//Code to check winner, if winner, change value of isGameOver
		winner = game.checkWinner();
		
		//Return winner
		return winner;
	}
	
	public String getGameDisplay()
	{
		String board_to_display = "GetGameDisplay_Successful";
		//Code to get Display
		int [][] board = game.getBoard();
		
		board_to_display = "";
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				if(board[i][j] == 0)
					board_to_display += "   ";
				else
				{
					board_to_display += " " + game.getPlayer(board[i][j]).getMarker() + " ";
				}
				if(j!=2)
					board_to_display += "|";
				else
					board_to_display += "\n";
			}
			if(i!=2)
				board_to_display += "---|---|---\n";
		}
		//Return Board
		return board_to_display;
	}
	
	public TTTGame getGame() {
		return game;
	}
}