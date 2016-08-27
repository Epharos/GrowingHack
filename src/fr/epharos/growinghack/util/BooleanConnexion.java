package fr.epharos.growinghack.util;

public class BooleanConnexion 
{
	public static boolean playerInfos = false;
	public static boolean playerApp = true;
	
	public static void reset()
	{
		BooleanConnexion.playerApp = false;
		BooleanConnexion.playerInfos = false;
	}
}
