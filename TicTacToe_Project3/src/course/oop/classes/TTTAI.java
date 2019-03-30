package course.oop.classes;

public class TTTAI extends TTTPlayer
{
	private int difficultyLvl;
	
	public TTTAI()
	{
		username = "Player X";
		marker = "O";
		isAi = true;
		difficultyLvl = 1;
	}
	
	public TTTAI(String newName)
	{
		username = newName;
		marker = "O";
		isAi = true;
		difficultyLvl = 1;	
	}
	
	public TTTAI(String newName, int diff)
	{
		username = newName;
		marker = "O";
		isAi = true;
		difficultyLvl = diff;
	}
	
	public void setDifficulty(int newDiff) {
		difficultyLvl = newDiff;
	}
	
	public int getDifficulty() {
		return difficultyLvl;
	}
}