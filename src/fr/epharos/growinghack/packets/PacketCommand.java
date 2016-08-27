package fr.epharos.growinghack.packets;

public class PacketCommand extends Packet 
{
	public String command;

	public void handlePacket(Handler handler, int connexionID) 
	{
		handler.handleCommand(this, connexionID);
	}
}
