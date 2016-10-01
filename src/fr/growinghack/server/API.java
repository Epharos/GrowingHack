package fr.growinghack.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import fr.growinghack.GrowingHack;
import fr.growinghack.util.ImageEncoding;

public class API 
{
	private static File getPlayerFile(String username)
	{
		return new File("accounts/" + username);
	}
	
	private static File getPlayerFile(String username, String file)
	{
		return new File("accounts/" + username + "/" + file + ".txt");
	}
	
	public static List<String> getPlayerInformations(String username)
	{
		File file = getPlayerFile(username, "informations");
		
		List<String> content = new ArrayList<String>();
		
		try
		{
			InputStream is = new FileInputStream(file);
			
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			
			try 
			{
				while((line = br.readLine()) != null)
				{
					content.add(line);
				}
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return content;
	}
	
	public static Object getPlayerInformation(List<String> content, String info)
	{
		for(String s : content)
		{
			String[] values = s.split(":");
			
			if(values[0].equals(info))
			{
				return values[1];
			}
		}
		
		return null;
	}
	
	public static List<String> getPlayerContacts(String username)
	{
		File file = getPlayerFile(username, "contactslist");
		
		List<String> contacts = new ArrayList<String>();
		
		try
		{
			InputStream is = new FileInputStream(file);
			
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			
			try 
			{
				while((line = br.readLine()) != null)
				{
					if(API.getPlayerFile(line).exists())
					{
						contacts.add(line);
					}
				}
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println(contacts.size() + " contacts");
		
		return contacts;
	}
	
	public static List<String> getPlayerContacts(int connexionId)
	{
		String username = null;
		
		for(ConnectedPlayer cp : GrowingHack.instance.server.connected)
		{
			if(cp.id == connexionId)
			{
				username = cp.username;
			}
		}
		
		return API.getPlayerContacts(username);
	}
	
	public static boolean isPlayerConnected(String username)
	{
		for(ConnectedPlayer cp : GrowingHack.instance.server.connected)
		{
			if(cp.username.equals(username))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static byte[] getPlayerImage(String username)
	{
		File image = new File("accounts/" + username + "/avatar.jpg");
		
		if(image.exists())
		{
			return ImageEncoding.imageToBytes(image, "jpg");
		}
		
		return null;
	}
}
