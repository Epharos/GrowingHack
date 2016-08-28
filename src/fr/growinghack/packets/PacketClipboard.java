package fr.growinghack.packets;

public class PacketClipboard extends Packet 
{
	public String clipboard;

	public void handlePacket(Handler handler, int connexionID) 
	{
		handler.handleClipboard(this);		
	}
}
