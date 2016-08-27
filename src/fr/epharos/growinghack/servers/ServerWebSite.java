package fr.epharos.growinghack.servers;

import java.util.ArrayList;
import java.util.List;

import fr.epharos.growinghack.files.File;

public abstract class ServerWebSite extends Server 
{
	public final String url;
	public List<File> files = new ArrayList<File>();
	
	public ServerWebSite(String url)
	{
		this.url = url;
	}
	
	public void onUpdate() 
	{
		
	}
}
