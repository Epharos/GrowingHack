package fr.epharos.growinghack.command;

import fr.epharos.growinghack.GrowingHack;
import fr.epharos.growinghack.packets.PacketIP;

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
					packet.crypted = true;
				}
				
				if(args.length > 2)
				{
					if(args[2] != null)
					{
						packet.copy = args[2].equals("copy");
					}
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
