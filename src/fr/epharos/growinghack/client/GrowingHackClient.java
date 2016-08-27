package fr.epharos.growinghack.client;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import fr.epharos.growinghack.packets.HandlerClient;
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
import fr.epharos.growinghack.screen.RegisterScreen;
import fr.epharos.growinghack.util.ImageEncoding;

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
	 * @TODO Créer une classe spécifique à la création de compte
	 */
	public void createAccount(String username, String password, String cpassword, String mail, String cmail, String avatar)
	{
		PacketCreateAccount packet = new PacketCreateAccount();
		boolean send = true;
		
		if(!avatar.equals("Avatar"))
		{
			File image = new File(avatar);
			
			if(!image.exists())
			{
				RegisterScreen.errorMessage = "Votre image n'existe pas.";
				send = false;
			}
			else
			{
				Texture img = new Texture(Gdx.files.absolute(avatar));
				
				if(img.getWidth() > 200 || img.getHeight() > 200 || !avatar.endsWith(".jpg"))
				{
					if(img.getWidth() > 200 || img.getHeight() > 200)
					{
						RegisterScreen.errorMessage = "Votre image n'est pas dans les bonnes dimensions (200x200 maximum).";
					}
					
					if(!avatar.endsWith(".jpg"))
					{
						RegisterScreen.errorMessage = "Votre image doit être sous format jpg.";
					}
					
					send = false;
				}
				else
				{
					packet.avatar = ImageEncoding.imageToBytes(image, "jpg");
				}
			}
		}
		else
		{
			packet.useDefaultAvatar = true;
		}
		
		if(!mail.equals(cmail))
		{
			RegisterScreen.errorMessage = "Vos mails ne correspondent pas.";
			send = false;
		}
		
		if(!mail.contains("@") || mail.equals(""))
		{
			RegisterScreen.errorMessage = "Votre adresse mail n'est pas conforme.";
			send = false;
		}
		
		if(!password.equals(cpassword))
		{
			RegisterScreen.errorMessage = "Vos mots de passes ne correspondent pas.";
			send = false;
		}
		
		if(password.equals("") || password.length() < 6 || password.length() > 48)
		{
			RegisterScreen.errorMessage = "Votre mot de passe n'est pas conforme" + (password.length() > 48 ? ", il est trop long." : "") + (password.length() < 6 ? ", il est trop court." : "") + (password.equals("") ? "." : "");
			send = false;
		}
		
		if(username.equals("") || username.length() < 3 || username.length() > 18)
		{
			RegisterScreen.errorMessage = "Votre nom de compte n'est pas conforme" + (username.length() > 18 ? ", il est trop long." : "") + (username.length() < 3 ? ", il est trop court." : "") + (username.equals("") ? "." : "");
			send = false;
		}
		
		if(send)
		{
			packet.username = username;
			
			try
			{
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] bytes = password.getBytes("UTF-8");
				md.reset();
				md.update(bytes, 0, password.length());
				byte[] crypted = md.digest(bytes);
				packet.password = new String(new BigInteger(1, crypted).toString(16));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			packet.mail = mail;
			
			this.client.sendTCP(packet);
		}
	}
	
	/** Connexion du client
	 * 
	 * @param username : pseudo de l'utilisateur
	 * @param password : mot de passe
	 * 
	 * @TODO Créer une classe spécifique à la connexion
	 */
	public void connectClient(String username, String password)
	{
		PacketConnection packet = new PacketConnection();
		packet.username = username;
		
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = password.getBytes("UTF-8");
			md.reset();
			md.update(bytes, 0, password.length());
			byte[] crypted = md.digest(bytes);
			packet.password = new String(new BigInteger(1, crypted).toString(16));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		this.client.sendTCP(packet);
	}
}
