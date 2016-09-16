package fr.growinghack.packets;

public class PacketClientInfos extends Packet 
{
	public int level, money, exp;
	public String username;
	
	public void handlePacket(Handler handler, int connexionID) 
	{
		handler.handleClientInfos(this, connexionID);
	}
}
