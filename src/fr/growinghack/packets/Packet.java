package fr.growinghack.packets;

import java.util.ArrayList;
import java.util.List;

public abstract class Packet 
{
	public abstract void handlePacket(Handler handler, int connexionID);
	
	private final static void registerPacket(Class<?> cl)
	{
		Packet.classes.add(cl);
	}
	
	public static final void registerPackets()
	{
		Packet.registerPacket(Packet.class);
		Packet.registerPacket(PacketUsername.class);
		Packet.registerPacket(PacketCreateAccount.class);
		Packet.registerPacket(PacketAuthentificationError.class);
		Packet.registerPacket(PacketConnection.class);
		Packet.registerPacket(PacketConnexionScreenInfos.class);
		Packet.registerPacket(byte[].class);
		Packet.registerPacket(PacketUserImage.class);
		Packet.registerPacket(PacketClientInfos.class);
		Packet.registerPacket(PacketPing.class);
		Packet.registerPacket(PacketCommand.class);
		Packet.registerPacket(PacketTerminal.class);
		Packet.registerPacket(String[].class);
		Packet.registerPacket(PacketIP.class);
		Packet.registerPacket(PacketClipboard.class);
		Packet.registerPacket(ArrayList.class);
		Packet.registerPacket(List.class);
		Packet.registerPacket(PacketMessagerie.class);
	}
	
	public static List<Class<?>> classes = new ArrayList<Class<?>>();
}
