package fr.growinghack.command;

import fr.growinghack.GrowingHack;
import fr.growinghack.packets.PacketIP;

public class CommandIP extends Command 
{
	public CommandIP(String name, boolean c, boolean s) 
	{
		super(name, c, s);
	}

	public void execute(String[] args, boolean client, boolean server, int i) 
	{
		if(client)
		{
			PacketIP packet = new PacketIP();
			
			if(args.length > 1)
			{
				if(args[1] != null)
				{
					packet.username = args[1];
				}
			}
			else
			{
				packet.username = GrowingHack.currentUser.username;
			}
			
			GrowingHack.instance.client.client.sendTCP(packet);
		}
	}
}
