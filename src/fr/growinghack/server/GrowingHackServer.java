package fr.growinghack.server;

import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import fr.growinghack.packets.HandlerServer;
import fr.growinghack.packets.Packet;

public class GrowingHackServer 
{
	public Server server;
	private Kryo kryo;
	
	public List<ConnectedPlayer> connected = new ArrayList<ConnectedPlayer>();
	
	private static final int tcp = 2406;
	private static final int udp = 2406;
	
	public static final String build = "indev";
	
	public static HandlerServer handler = new HandlerServer();
	
	public GrowingHackServer()
	{
		server = new Server(1000000, 1000000);
		kryo = server.getKryo();
		
		Packet.registerPackets();
		
		for(Class<?> cl : Packet.classes)
		{
			kryo.register(cl);
		}
		
		server.addListener(new Listener()
		{
			public void connected(Connection connection)
			{
				
			}
			
			public void disconnected(Connection connection)
			{
				for(ConnectedPlayer cp : connected)
				{
					if(cp.id == connection.getID())
					{
						connected.remove(cp);
					}
				}
			}
			
			public void received(Connection connection, Object o)
			{							
				if(o instanceof Packet)
				{
					((Packet) o).handlePacket(GrowingHackServer.handler, connection.getID());
				}
			}
		});
		
		try
		{
			server.bind(tcp, udp);
			server.start();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void pr(Object o)
	{
		System.out.println("[SERVER] " + String.valueOf(o));
	}
}
