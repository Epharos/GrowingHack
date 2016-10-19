package fr.growinghack;

import com.badlogic.gdx.Game;

import fr.growinghack.client.GrowingHackClient;
import fr.growinghack.client.User;
import fr.growinghack.os.OS;
import fr.growinghack.packets.PacketUserImage;
import fr.growinghack.screen.ConnexionScreen;
import fr.growinghack.server.GrowingHackServer;
import fr.growinghack.util.Font;

public class GrowingHack extends Game
{	
	public static GrowingHack instance; /** Instance du jeu **/
	
	public static User currentUser; /** Instance de l'utilisateur **/
	
	public GrowingHackClient client; /** Client **/
	public GrowingHackServer server; /** Serveur **/
	
	public static OS currentOS; /** Instance statique de l'Operating System **/
	
	public static String credentialsUsername, credentialsPassword;
	
	public void setCredentials(String s1, String s2)
	{
		GrowingHack.credentialsUsername = s1;
		GrowingHack.credentialsPassword = s2;
	}
	
	/** Initialisation de l'�cran **/
	public void create() 
	{
		GrowingHack.instance = this;
		new Font();
		this.server = new GrowingHackServer();
		this.client = new GrowingHackClient();
		this.setScreen(new ConnexionScreen(this));
	}
	
	/** Cette fonction est appel�e lorsque le client a r�ussi l'authentification 
	 * 
	 * @param s : le pseudo du client
	 */
	public void onClientConnected(String s)
	{
		GrowingHack.currentUser = new User().setUsername(s);
		PacketUserImage packet2 = new PacketUserImage();
		packet2.username = GrowingHack.currentUser.username;
		this.client.client.sendTCP(packet2);
		ConnexionScreen.userConnected = true;
	}
}
