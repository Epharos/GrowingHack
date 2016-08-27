package fr.epharos.growinghack.packets;

public class PacketAuthentificationError extends Packet 
{
	public int error;
	public String username;
	
	public void handlePacket(Handler handler, int connexionID) 
	{
		handler.handleAuthentificationError(this);
	}
}
