package fr.growinghack.packets;

public class PacketIP extends Packet
{
	public String username;
	
	public void handlePacket(Handler handler, int connexionID) 
	{
		handler.handleIP(this, connexionID);
	}
}
