package fr.epharos.growinghack.servers;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ServerList 
{
	private static List<Server> servers = new ArrayList<Server>();
	private static Hashtable<String, ServerWebSite> websites = new Hashtable<String, ServerWebSite>();
	
	public static void addServer(Server server)
	{
		if(server instanceof ServerWebSite)
		{
			ServerList.websites.put(((ServerWebSite) server).url, (ServerWebSite) server);
			return;
		}
		
		ServerList.servers.add(server);
	}
	
	public static void removeServer(Server server)
	{
		if(server instanceof ServerWebSite)
		{
			ServerList.websites.remove(((ServerWebSite) server).url);
			return;
		}
		
		ServerList.servers.remove(server);
	}
	
	public static Server getServer(int index)
	{		
		return ServerList.servers.get(index);
	}
	
	public static ServerWebSite getServerSite(String url)
	{
		return ServerList.websites.get(url);
	}
}
