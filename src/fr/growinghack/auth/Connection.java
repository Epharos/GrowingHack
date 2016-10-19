package fr.growinghack.auth;

import java.math.BigInteger;
import java.security.MessageDigest;

import fr.growinghack.GrowingHack;
import fr.growinghack.packets.PacketConnection;

public class Connection 
{
	/** Connecte le client apr�s avoir crypt� le mot de passe
	 * 
	 * @param username : pseudo de l'utilisateur
	 * @param password : mot de passe en clair
	 */
	public static void connectClient(String username, String password)
	{
		PacketConnection packet = new PacketConnection();
		packet.username = username;
		
		try /** Tout ce tronc "try" crypte le mot de passe en MD5 **/
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
		
		GrowingHack.instance.client.client.sendTCP(packet);
	}
}
