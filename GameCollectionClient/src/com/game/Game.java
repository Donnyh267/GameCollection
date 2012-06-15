package com.game;

public class Game {
	
	private long id;
	private boolean visible;
	private String title;
	private String platform;
	private String genre;
	
	public Game() {
		id = (long) 0;
		visible = true;
		title = "Super Mario Bros";
		platform = "NES";
		genre = "Platformer";
	}
	
	public Game(String n, String p, String g) {
		id = (long) 0;
		visible = true;
		title = n;
		platform = p;
		genre = g;
	}
	
	public boolean getVisible() {
		return visible;
	}
	
	public void setVisible(boolean b) {
		visible = b;
	}
	
	public long getID(){
		return id;
	}
	
	public void setID(long newID) {
		id = newID;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String name) {
		this.title = name;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	
}