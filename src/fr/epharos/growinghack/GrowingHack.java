package fr.epharos.growinghack;

import com.badlogic.gdx.Game;

import fr.epharos.growinghack.client.GrowingHackClient;
import fr.epharos.growinghack.client.User;
import fr.epharos.growinghack.os.OS;
import fr.epharos.growinghack.packets.PacketUserImage;
import fr.epharos.growinghack.screen.ConnexionScreen;
import fr.epharos.growinghack.server.GrowingHackServer;
import fr.epharos.growinghack.util.Font;

public class GrowingHack extends Game
{	
	public static GrowingHack instance; /** Instance du jeu **/
	
	public static User currentUser; /** Instance de l'utilisateur **/
	
	public GrowingHackClient client; /** Client **/
	public GrowingHackServer server; /** Serveur **/
	
	public static OS currentOS; /** Instance statique de l'Operating System **/
	
	/** Initialisation de l'écran **/
	public void create() 
	{
		GrowingHack.instance = this;
		new Font();
		this.server = new GrowingHackServer();
		this.client = new GrowingHackClient();
		this.setScreen(new ConnexionScreen(this));
	}
	
	/** Cette fonction est appelée lorsque le client a réussi l'authentification 
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
