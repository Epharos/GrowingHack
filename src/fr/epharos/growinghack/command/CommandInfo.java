package fr.epharos.growinghack.command;

import fr.epharos.growinghack.GrowingHack;
import fr.epharos.growinghack.client.GrowingHackClient;
import fr.epharos.growinghack.packets.PacketTerminal;
import fr.epharos.growinghack.server.GrowingHackServer;

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
