package fr.epharos.growinghack.packets;

public class PacketConnection extends Packet
{
	public String username;
	public String password;

	public void handlePacket(Handler handler, int connexionID) 
	{
		handler.handleConnection(this, connexionID);		
	}
}
