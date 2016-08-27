package fr.epharos.growinghack.packets;

public class PacketUserImage extends Packet 
{
	public String username;
	public byte[] image;
	
	public void handlePacket(Handler handler, int connexionID) 
	{
		handler.handleUserImage(this, connexionID);
	}
}
