package fr.epharos.growinghack.packets;

public class PacketConnexionScreenInfos extends Packet
{
	public int registered, connected;

	public void handlePacket(Handler handler, int connexionID) 
	{
		handler.handleConnexionScreenInfos(this, connexionID);
	}
	
}
