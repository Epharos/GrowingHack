package fr.epharos.growinghack.packets;

public class PacketPing extends Packet 
{
	public long start = System.currentTimeMillis();

	public void handlePacket(Handler handler, int connexionID) 
	{
		handler.handlePing(this, connexionID);
	}
}
