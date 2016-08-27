package fr.epharos.growinghack.packets;

public class PacketCreateAccount extends Packet
{
	public String username = "";
	public String password = "";
	public String mail = "";
	public byte[] avatar;
	public boolean useDefaultAvatar = false;
	
	public void handlePacket(Handler handler, int connexionID) 
	{
		handler.handleCreateAccount(this, connexionID);
	}
}
