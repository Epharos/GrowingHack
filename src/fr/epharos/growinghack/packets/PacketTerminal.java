package fr.epharos.growinghack.packets;

public class PacketTerminal extends Packet 
{
	public String[] lines = null;
	
	public void setLines(String ... s)
	{
		this.lines = s;
	}

	public void handlePacket(Handler handler, int connexionID) 
	{
		handler.handleTerminal(this);
	}
}
