package fr.epharos.growinghack.server;

import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import fr.epharos.growinghack.packets.HandlerServer;
import fr.epharos.growinghack.packets.Packet;
import fr.epharos.growinghack.packets.PacketAuthentificationError;
import fr.epharos.growinghack.packets.PacketClientInfos;
import fr.epharos.growinghack.packets.PacketClipboard;
import fr.epharos.growinghack.packets.PacketCommand;
import fr.epharos.growinghack.packets.PacketConnection;
import fr.epharos.growinghack.packets.PacketConnexionScreenInfos;
import fr.epharos.growinghack.packets.PacketCreateAccount;
import fr.epharos.growinghack.packets.PacketIP;
import fr.epharos.growinghack.packets.PacketPing;
import fr.epharos.growinghack.packets.PacketTerminal;
import fr.epharos.growinghack.packets.PacketUserImage;
import fr.epharos.growinghack.packets.PacketUsername;

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
		
		kryo.register(Packet.class);
		kryo.register(PacketUsername.class);
		kryo.register(PacketCreateAccount.class);
		kryo.register(PacketAuthentificationError.class);
		kryo.register(PacketConnection.class);
		kryo.register(PacketConnexionScreenInfos.class);
		kryo.register(byte[].class);
		kryo.register(PacketUserImage.class);
		kryo.register(PacketClientInfos.class);
		kryo.register(PacketPing.class);
		kryo.register(PacketCommand.class);
		kryo.register(PacketTerminal.class);
		kryo.register(String[].class);
		kryo.register(PacketIP.class);
		kryo.register(PacketClipboard.class);
		
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
