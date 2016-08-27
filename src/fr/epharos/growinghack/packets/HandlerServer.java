package fr.epharos.growinghack.packets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import fr.epharos.growinghack.GrowingHack;
import fr.epharos.growinghack.command.Command;
import fr.epharos.growinghack.server.ConnectedPlayer;
import fr.epharos.growinghack.util.IP;
import fr.epharos.growinghack.util.ImageEncoding;

public class HandlerServer extends Handler 
{
	private GrowingHack gh = GrowingHack.instance;
	
	public void handleConnexionScreenInfos(PacketConnexionScreenInfos packet, int connexionID) 
	{
		packet.connected = this.gh.server.connected.size();
		File file = new File("accounts/");
		int i = file.listFiles().length;
		packet.registered = i;
		this.gh.server.server.sendToTCP(connexionID, packet);
	} 
	
	public void handleCommand(PacketCommand packet, int connexionID) 
	{
		Command.executeServer(packet.command.split(" "), connexionID);
	}
	
	public void handleTerminal(PacketTerminal packet, int connexionID) 
	{
		this.gh.server.server.sendToTCP(connexionID, packet);
	}
	
	public void handleClientInfos(PacketClientInfos packet, int connexionID) 
	{
		File user = new File("accounts/" + packet.username + "/informations.txt");
		
		List<String> content = new ArrayList<String>();
		
		try 
		{
			InputStream is = new FileInputStream(user);
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
			
			for(String s : content)
			{
				String[] values = s.split(":");
				
				if(values[0].equals("money"))
				{
					packet.money = Integer.valueOf(values[1]);
				}
				
				if(values[0].equals("level"))
				{
					packet.level = Integer.valueOf(values[1]);
				}
				
				if(values[0].equals("exp"))
				{
					packet.exp = Integer.valueOf(values[1]);
				}
				
				if(values[0].equals("title"))
				{
					if(values[1] != null)
					{
						packet.title = values[1];
					}
					else
					{
						packet.title = "";
					}
				}
				
				if(values[0].equals("desc"))
				{
					if(values[1] != null)
					{
						if(values[1].length() > 256)
						{
							packet.description = values[1].substring(0, 256);
						}
						else
						{
							packet.description = values[1];
						}
					}
					else
					{
						packet.description = "";
					}
				}
				
				if(values[0].equals("type"))
				{
					packet.type = Integer.valueOf(values[1]);
				}
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		GrowingHack.instance.server.server.sendToTCP(connexionID, packet);
	}
	
	public void handleIP(PacketIP packet, int connexionID) 
	{
		File user = new File("accounts/" + packet.username + "/informations.txt");
		
		List<String> content = new ArrayList<String>();
		
		try 
		{
			InputStream is = new FileInputStream(user);
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
			
			for(String s : content)
			{
				String[] values = s.split(":");
				String ip = "";
				
				if(values[0].equals("ip"))
				{
					ip = values[1];
					
					PacketTerminal packet1 = new PacketTerminal();
					packet1.setLines("IP " + packet.username + " > " + ip, "");
					GrowingHack.instance.server.server.sendToTCP(connexionID, packet1);
					
					if(packet.copy)
					{
						PacketClipboard packet2 = new PacketClipboard();
						packet2.clipboard = ip;
						GrowingHack.instance.server.server.sendToTCP(connexionID, packet2);
					}
				}
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void handleUserImage(PacketUserImage packet, int connexionID) 
	{
		File file = new File("accounts/" + packet.username + "/avatar.jpg");
		packet.image = ImageEncoding.imageToBytes(file, "jpg");
		
		this.gh.server.server.sendToTCP(connexionID, packet);
	}
	
	public void handlePing(PacketPing packet, int connexionID) 
	{
		this.gh.server.server.sendToTCP(connexionID, packet);
	} 
	
	public void handleConnection(PacketConnection packet, int connexionID) 
	{
		File file = new File("accounts/" + packet.username);
		
		if(!file.exists())
		{
			PacketAuthentificationError send = new PacketAuthentificationError();
			send.error = 0;
			this.gh.server.server.sendToTCP(connexionID, send);
			return;
		}
		
		File user = new File("accounts/" + packet.username + "/user.txt");
		
		List<String> content = new ArrayList<String>();
		
		try 
		{
			InputStream is = new FileInputStream(user);
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
			
			for(String s : content)
			{
				String[] values = s.split(":");
				
				if(values[0].equals("password"))
				{
					if(values[1].equals(packet.password))
					{
						PacketAuthentificationError send = new PacketAuthentificationError();
						send.error = 1337;
						send.username = packet.username;
						this.gh.server.server.sendToTCP(connexionID, send);
						ConnectedPlayer cp = new ConnectedPlayer();
						cp.id = connexionID;
						cp.username = packet.username;
						this.gh.server.connected.add(cp);
						return;
					}
				}
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void handleCreateAccount(PacketCreateAccount packet, int connexionID) 
	{
		if(!new File("accounts/").exists())
		{
			new File("accounts/").mkdir();
		}
		
		File file = new File("accounts/" + packet.username);
		
		if(file.exists())
		{
			PacketAuthentificationError send = new PacketAuthentificationError();
			send.error = 1;
			this.gh.server.server.sendToTCP(connexionID, send);
			return;
		}
		else
		{						
			try 
			{
				file.mkdir();
				
				File user = new File("accounts/" + packet.username + "/user.txt");
				user.createNewFile();
				FileWriter fr = new FileWriter(user);
				BufferedWriter bw = new BufferedWriter(fr);
				
				bw.write("username:" + packet.username + "\r\n");
				bw.write("password:" + packet.password + "\r\n");
				bw.write("mail:" + packet.mail + "\r\n");
				bw.close();
				
				File user2 = new File("accounts/" + packet.username + "/informations.txt");
				user2.createNewFile();
				FileWriter fr2 = new FileWriter(user2);
				BufferedWriter bw2 = new BufferedWriter(fr2);
				
				bw2.write("level:1\r\n");
				bw2.write("exp:0\r\n");
				bw2.write("money:0\r\n");
				bw2.write("title:Joueur\r\n");
				bw2.write("desc:\r\n");
				
				bw2.write("ip:" + IP.generateIp());
				
				bw2.close();
				
				ImageEncoding.bytesToImage(packet.avatar, new File("accounts/" + packet.username + "/avatar.jpg"), "jpg");
				
				PacketAuthentificationError send = new PacketAuthentificationError();
				send.error = 42;
				this.gh.server.server.sendToTCP(connexionID, send);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
