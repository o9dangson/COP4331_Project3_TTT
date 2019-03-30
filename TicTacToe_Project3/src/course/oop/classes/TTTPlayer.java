package course.oop.classes;

import java.io.Serializable;

public class TTTPlayer implements Serializable
{
	protected String username;
	protected String marker;
	protected int recordWin;
	protected int recordLost;
	protected boolean isAi;
	
	public TTTPlayer() 
	{
		username = "Player";
		marker = "X";
		isAi = false;
		recordWin = 0;
		recordLost = 0;
	}
	
	public TTTPlayer(int playerNum) 
	{
		username = "Player " + Integer.toString(playerNum);
		if(playerNum == 1)
			marker = "X";
		else
			marker = "O";
		isAi = false;
		recordWin = 0;
		recordLost = 0;
	}
	
	public TTTPlayer(String name, String marking, int recW, int recL) 
	{
		username = name;
		marker = marking;
		isAi = false;
		recordWin = recW;
		recordLost = recL;
	}
	
	public void setName(String newName) {
		username = newName;
	}
	
	public String getName() {
		return username;
	}
	
	public void setMarker(String newMarker) {
		marker = newMarker;
	}
	
	public String getMarker() {
		return marker;
	}
	
	public void setAi(boolean n_isAi) {
		isAi = n_isAi;
	}
	
	public boolean getAi() {
		return isAi;
	}
	
	//Altered to RecordWin and RecordLost to includes win/lost ratio
	public int getRecordWin() {
		return recordWin;
	}
	
	public void setRecordWin(int newR) {
		recordWin = newR;
	}
	
	public int getRecordLost() {
		return recordLost;
	}
	
	public void setRecordLost(int newR) {
		recordLost = newR;
	}
	
	@Override
    public String toString() {
        return "TTTPlayer [userName=" + username + ", marker=" + marker 
        		+ ", recordWin=" + recordWin + ", recordLost=" + recordLost
                + "]";
    }
}