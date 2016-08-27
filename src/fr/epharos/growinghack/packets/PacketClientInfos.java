package fr.epharos.growinghack.packets;

public class PacketClientInfos extends Packet 
{
	public int level, money, exp, type;
	public String username, description, title;
	
	public void handlePacket(Handler handler, int connexionID) 
	{
		handler.handleClientInfos(this, connexionID);
	}
}
