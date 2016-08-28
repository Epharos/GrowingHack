package fr.growinghack.packets;

public abstract class Packet 
{
	public abstract void handlePacket(Handler handler, int connexionID);
}
