package fr.epharos.growinghack.servers;

import fr.epharos.growinghack.util.IP;

public abstract class Server 
{
	public int ram = 1;
	public String ip = IP.generateIp(), password = IP.generatePassword(12);
	
	public Server setRAM(int ram)
	{
		this.ram = ram;
		return this;
	}
	
	public Server setIP(String ip)
	{
		this.ip = ip;
		return this;
	}
	
	public Server setPassword(String password)
	{
		this.password = password;
		return this;
	}
	
	public abstract void onUpdate();
}
