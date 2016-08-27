package fr.epharos.growinghack.icon;

import fr.epharos.growinghack.application.Application;

public class WebBrowser extends Icon
{
	public String getAppName() 
	{
		return "Navigateur Internet";
	}

	public String getAppIcon() 
	{
		return "internet";
	}

	public Application openApp() 
	{
		return new fr.epharos.growinghack.application.WebBrowser();
	}

	public int getAppID() 
	{
		return 2;
	}
}
