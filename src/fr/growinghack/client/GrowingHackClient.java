package fr.growinghack.client;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import fr.growinghack.auth.AccountCreation;
import fr.growinghack.packets.HandlerClient;
import fr.growinghack.packets.Packet;

public class GrowingHackClient 
{
	public Client client; /** Kryonet Client **/
	private Kryo kryo; /** Kryonet Packets **/
	
	private static final int tcp = 2406; /** Port TCP **/
	private static final int udp = 2406; /** Port UDP **/
	
	private static final String ip = "localhost"; /** IP de connexion au serveur **/
	
	public static final String build = "indev"; /** Version du client **/
	
	public static String clipboard = ""; /** Clipboard (pour les "copier/coller") **/
	
	private static final HandlerClient handler = new HandlerClient(); /** Le manipulateur de packets **/
	
	public GrowingHackClient()
	{
		client = new Client(1000000, 1000000);
		client.start();
		kryo = client.getKryo();
		
		/** Enregistrement des packets
		 * @TODO Mettre une fonction statique dans "Handler"
		 */
		
		Packet.registerPackets();
		
		for(Class<?> cl : Packet.classes)
		{
			kryo.register(cl);
		}
		
		client.addListener(new Listener()
		{			
			public void connected(Connection connection)
			{
				
			}
			
			public void disconnected(Connection connection)
			{
				
			}
			
			/** Traitement des packets reçu par le HandlerClient **/
			public void received(Connection connection, Object o)
			{				
				if(o instanceof Packet)
				{
					((Packet) o).handlePacket(GrowingHackClient.handler, connection.getID());
				}
			}
		});
		
		try
		{
			client.connect(10000, ip, tcp, udp);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/** Affiche un message dans la console
	 * 
	 * @param o : l'objet à afficher
	 */
	public static void pr(Object o)
	{
		System.out.println("[CLIENT] " + String.valueOf(o));
	}
	
	/** Crée un compte
	 * 
	 * @param username : nom d'utilisateur
	 * @param password : mot de passe
	 * @param cpassword : confirmation du mot de passe
	 * @param mail : mail
	 * @param cmail : confirmation du mail
	 * @param avatar : chemin absolu vers l'image utilisateur
	 * 
	 */
	public void createAccount(String username, String password, String cpassword, String mail, String cmail, String avatar)
	{
		AccountCreation.createAccount(username, password, cpassword, mail, cmail, avatar);
	}
	
	/** Connexion du client
	 * 
	 * @param username : pseudo de l'utilisateur
	 * @param password : mot de passe
	 * 
	 */
	public void connectClient(String username, String password)
	{
		fr.growinghack.auth.Connection.connectClient(username, password);
	}
}
