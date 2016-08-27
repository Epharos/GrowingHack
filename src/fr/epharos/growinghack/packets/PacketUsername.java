package fr.epharos.growinghack.packets;

public class PacketUsername extends Packet
{
	public String pseudo;

	public void handlePacket(Handler handler, int connexionID) 
	{
		handler.handleUsername(this);
	}
}
