package fr.epharos.growinghack.client;

public class User 
{
	public String username = "Epharos";
	public String title = "Cyberpirate";
	public String description = "";
	public int type = -1;
	
	public int level = 1;
	public int experience = 0;
	
	public int money = 0;
	
	public long ping = 0;
	
	public int capacity = 1;
	public int servers = 0;
	public int connection = 1000;
	
	public User setUsername(String s)
	{
		this.username = s;
		return this;
	}
}
