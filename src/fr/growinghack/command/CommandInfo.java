package fr.growinghack.command;

import fr.growinghack.GrowingHack;
import fr.growinghack.client.GrowingHackClient;
import fr.growinghack.packets.PacketTerminal;
import fr.growinghack.server.GrowingHackServer;

public class CommandInfo extends Command 
{
	public CommandInfo(String name, boolean c, boolean s) 
	{
		super(name, c, s);
	}

	public void execute(String[] args, boolean client, boolean server, int i) 
	{
		if(client)
		{
			PacketTerminal packet = new PacketTerminal();
			packet.setLines("Client is running on -" + GrowingHackClient.build + "- version");
			GrowingHack.instance.client.client.sendTCP(packet);
		}
		
		if(server)
		{
			PacketTerminal packet = new PacketTerminal();
			packet.setLines("Server is running on -" + GrowingHackServer.build + "- version", "");
			GrowingHack.instance.server.server.sendToTCP(i, packet);
		}
	}
}
