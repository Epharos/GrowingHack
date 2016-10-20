package fr.growinghack.packets;

import java.io.File;

import fr.growinghack.GrowingHack;
import fr.growinghack.application.Application;
import fr.growinghack.application.Messagerie;
import fr.growinghack.application.Terminal;
import fr.growinghack.client.GrowingHackClient;
import fr.growinghack.command.Command;
import fr.growinghack.screen.ConnexionScreen;
import fr.growinghack.screen.RegisterScreen;
import fr.growinghack.util.BooleanConnexion;
import fr.growinghack.util.ImageEncoding;
import fr.growinghack.util.Logs;

public class HandlerClient extends Handler 
{
	@SuppressWarnings("unused")
	private GrowingHack gh = GrowingHack.instance;
	
	public void handleAuthentificationError(PacketAuthentificationError packet) 
	{
		switch(packet.error)
		{
			case 0:
				Logs.error("Le nom de compte ou le mot de passe est incorrect");
				break;
			case 1:
				Logs.error("Un joueur est déjà enregistré avec ce pseudo");
				break;
			case 2:
				Logs.error("Cette adresse mail est déjà utiliée par un autre joueur");
				break;
			case 42:
				Logs.success("Le compte a bien été créé");
				break;
			case 3:
				Logs.error("Votre compte a été banni.");
				break;
			case 1337:
				Logs.success("Connexion en cours ...");
				GrowingHack.instance.onClientConnected(packet.username);
				break;
		}
	}
	
	public void handleClientInfos(PacketClientInfos packet, int connexionID) 
	{
		GrowingHack.currentUser.money = packet.money;
		GrowingHack.currentUser.experience = packet.exp;
		GrowingHack.currentUser.level = packet.level;
		
		BooleanConnexion.playerInfos = true;
	}
	
	public void handleClipboard(PacketClipboard packet) 
	{
		GrowingHackClient.clipboard = packet.clipboard;
	}
	
	public void handleCommand(PacketCommand packet, int connexionID) 
	{
		Command.executeClient(packet.command.split(" "), connexionID);
	}
	
	public void handleConnexionScreenInfos(PacketConnexionScreenInfos packet, int connexionID) 
	{
		ConnexionScreen.connected = packet.connected;
		ConnexionScreen.registered = packet.registered;
	} 
	
	public void handlePing(PacketPing packet, int connexionID) 
	{
		GrowingHack.currentUser.ping = System.currentTimeMillis() - packet.start;
	} 
	
	public void handleTerminal(PacketTerminal packet, int connexionID) 
	{
		for(String s : packet.lines)
		{
			if(s != null)
			{
				Terminal.addLines(s);
			}
		}
	}
	
	public void handleUserImage(PacketUserImage packet, int connexionID) 
	{
		File folder = new File("cache/");
		File file = new File("cache/" + packet.username + ".jpg");
		
		if(!folder.exists())
		{
			folder.mkdir();
		}
		
		if(!file.exists() && !packet.useDefaultImage)
		{
			ImageEncoding.bytesToImage(packet.image, file, "jpg");
		}
	}
	
	public void handleContact(PacketMessagerie packet, int connexionID) 
	{
		for (Application app : GrowingHack.currentOS.applications) 
		{
			if (app instanceof Messagerie) 
			{
				((Messagerie)app).contacts = packet.contacts;
			}
		}
	}
}
