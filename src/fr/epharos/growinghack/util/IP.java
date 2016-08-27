package fr.epharos.growinghack.util;

public class IP 
{
	private static final String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	public static String generateIp()
	{
		String ip = "";
		
		for(int i = 0 ; i < 4 ; i++)
		{
			ip = ip.concat((Math.random() * 254 + 1) + (i != 3 ? "." : ""));
		}
		
		return ip;
	}
	
	public static String generatePassword(int lenght)
	{
		String generatedPass = "";
		
		for(int i = 0 ; i < lenght ; i++)
		{
			generatedPass = generatedPass.concat(String.valueOf(IP.letters.charAt((int) (Math.random() * IP.letters.length()))));
		}
		
		return generatedPass;
	}
}
