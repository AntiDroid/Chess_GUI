package main_package;


public class Player {

	private int timeSec;
	private String name;
	private Boolean isWhite;
	
	public Player(String name, Boolean isW){
		this.name = name;
		this.isWhite = isW;
		timeSec = 0;
	}
	
	public String getName(){
		return name;
	}
	public Boolean getIsWhite(){
		return isWhite;
	}
	
	public int getTimeSec(){
		return timeSec;
	}
	
	public void increaseTimeSec(){
		timeSec++;
	}
}
