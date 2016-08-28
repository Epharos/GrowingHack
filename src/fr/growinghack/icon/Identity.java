package fr.growinghack.icon;

import fr.growinghack.application.Application;

public class Identity extends Icon 
{
	public String getAppName() 
	{
		return "Carte d'identité";
	}

	public String getAppIcon() 
	{
		return "identity";
	}

	public Application openApp() 
	{
		return new fr.growinghack.application.Identity();
	}

	public int getAppID() 
	{
		return 1;
	}
}
