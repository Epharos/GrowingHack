package fr.growinghack.util;

public class Logs
{
	private static void log(Object o)
	{
		System.out.println(o);
	}
	
	private static void err(Object o)
	{
		System.err.println(o);
	}
	
	public static void info(Object o)
	{
		Logs.log("INFO : " + o);
	}
	
	public static void severe(Object o)
	{
		Logs.err("SEVERE : " + o);
	}
	
	public static void important(Object o)
	{
		Logs.err("IMPORTANT : " + o);
	}
	
	public static void error(Object o)
	{
		Logs.err("ERREUR : " + o);
	}
	
	public static void success(Object o)
	{
		Logs.log("SUCCES : " + o);
	}
}
