package fr.epharos.growinghack.packets;

public class PacketIP extends Packet
{
	public String username;
	public boolean crypted = false;
	public boolean copy = false;
	
	public void handlePacket(Handler handler, int connexionID) 
	{
		handler.handleIP(this, connexionID);
	}
}
