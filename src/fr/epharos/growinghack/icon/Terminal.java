package fr.epharos.growinghack.icon;

import fr.epharos.growinghack.application.Application;

public class Terminal extends Icon
{
	public String getAppName() 
	{
		return "Terminal de commande";
	}

	public String getAppIcon() 
	{
		return "terminal";
	}

	public Application openApp() 
	{
		return new fr.epharos.growinghack.application.Terminal();
	}
	
	public int getAppID() 
	{
		return 0;
	}
}
