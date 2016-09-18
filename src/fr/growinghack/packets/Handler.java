package fr.growinghack.packets;

public class Handler 
{
	public void handleAuthentificationError(PacketAuthentificationError packetAuthentificationError) {}

	public void handleClientInfos(PacketClientInfos packetClientInfos, int connexionID) {}

	public void handleClipboard(PacketClipboard packetClipboard) {}

	public void handleCommand(PacketCommand packetCommand, int connexionID) {}

	public void handleConnection(PacketConnection packetConnection, int connexionID) {}

	public void handleConnexionScreenInfos(PacketConnexionScreenInfos packetConnexionScreenInfos, int connexionID) {}

	public void handleCreateAccount(PacketCreateAccount packetCreateAccount, int connexionID) {}

	public void handleIP(PacketIP packetIP, int connexionID) {}

	public void handlePing(PacketPing packetPing, int connexionID) {}

	public void handleTerminal(PacketTerminal packetTerminal, int connexionID) {}

	public void handleUserImage(PacketUserImage packetUserImage, int connexionID) {}
	
	public void handleContact (PacketMessagerie packetUserImage, int connexionID) {}

	public void handleUsername(PacketUsername packetUsername) {} 
}
