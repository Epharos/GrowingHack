package fr.growinghack.client;

public class User 
{
	public String username = "";
	
	public int level = 1;
	public int experience = 0;
	
	public int money = 0;
	
	public long ping = 0;
	
	public User setUsername(String s)
	{
		this.username = s;
		return this;
	}
}
