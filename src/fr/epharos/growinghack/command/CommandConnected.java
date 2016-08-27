package fr.epharos.growinghack.command;

import fr.epharos.growinghack.GrowingHack;
import fr.epharos.growinghack.packets.PacketTerminal;
import fr.epharos.growinghack.server.ConnectedPlayer;

public class CommandConnected extends Command
{
	long time;
	
	public CommandConnected(String name, boolean c, boolean s) 
	{
		super(name, c, s);
	}

	public void execute(String[] args, boolean client, boolean server, int i) 
	{
		this.time = System.currentTimeMillis();
		
		if(server)
		{			
			PacketTerminal packet = new PacketTerminal();
			packet.setLines("Il y a actuellement " + GrowingHack.instance.server.connected.size() + (GrowingHack.instance.server.connected.size() > 1 ? " joueurs connectés" : " joueur connecté"), "");
			GrowingHack.instance.server.server.sendToTCP(i, packet);
			
			if(args.length > 1)
			{
				if(args[1] != null)
				{
					if(args[1].equals("list"))
					{
						for(ConnectedPlayer cp : GrowingHack.instance.server.connected)
						{
							PacketTerminal packet2 = new PacketTerminal();
							packet2.setLines("- " + cp.username);
							GrowingHack.instance.server.server.sendToTCP(i, packet2);
						}
						
						PacketTerminal packet3 = new PacketTerminal();
						packet3.setLines("");
						GrowingHack.instance.server.server.sendToTCP(i, packet3);
					}
				}
			}
		}
	}
}
