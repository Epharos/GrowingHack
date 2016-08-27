package fr.epharos.growinghack.packets;

import java.io.File;

import fr.epharos.growinghack.GrowingHack;
import fr.epharos.growinghack.application.Terminal;
import fr.epharos.growinghack.client.GrowingHackClient;
import fr.epharos.growinghack.command.Command;
import fr.epharos.growinghack.screen.ConnexionScreen;
import fr.epharos.growinghack.screen.RegisterScreen;
import fr.epharos.growinghack.util.BooleanConnexion;
import fr.epharos.growinghack.util.ImageEncoding;

public class HandlerClient extends Handler 
{
	@SuppressWarnings("unused")
	private GrowingHack gh = GrowingHack.instance;
	
	public void handleAuthentificationError(PacketAuthentificationError packet) 
	{
		switch(packet.error)
		{
			case 0:
				ConnexionScreen.errorMessage = "Le nom de compte ou le mot de passe est incorrect.";
				break;
			case 1:
				RegisterScreen.errorMessage = "Un joueur est déjà enregistré avec ce pseudo.";
				break;
			case 2:
				RegisterScreen.errorMessage = "Cette adresse mail est déjà utilisée par un autre joueur.";
				break;
			case 42:
				RegisterScreen.errorMessage = "Le compte a bien été créé.";
				break;
			case 3:
				ConnexionScreen.errorMessage = "Votre compte a été banni.";
				break;
			case 1337:
				ConnexionScreen.errorMessage = "Connexion en cours ...";
				GrowingHack.instance.onClientConnected(packet.username);
				break;
		}
	}
	
	public void handleClientInfos(PacketClientInfos packet, int connexionID) 
	{
		GrowingHack.currentUser.money = packet.money;
		GrowingHack.currentUser.experience = packet.exp;
		GrowingHack.currentUser.level = packet.level;
		GrowingHack.currentUser.title = packet.title;
		GrowingHack.currentUser.description = packet.description;
		GrowingHack.currentUser.type = packet.type;
		
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
	
	public void handleTerminal(PacketTerminal packet) 
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
		File file = new File("cache/" + packet.username + ".jpg");
		
		ImageEncoding.bytesToImage(packet.image, file, "jpg");
	}
}
