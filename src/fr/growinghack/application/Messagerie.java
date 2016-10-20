package fr.growinghack.application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

import fr.growinghack.GrowingHack;
import fr.growinghack.packets.PacketMessagerie;
import fr.growinghack.packets.PacketUserImage;
import fr.growinghack.server.API;
import fr.growinghack.ui.TextField;
import fr.growinghack.util.Font;

public class Messagerie extends Application {

	public Texture background = new Texture(Gdx.files.internal("ui/white.png"));
	public Texture logo = new Texture(Gdx.files.internal("apps/messagerie.png"));
	public Texture blue = new Texture(Gdx.files.internal("ui/backgroundmessagerie.png"));
	public Texture avatar = new Texture(Gdx.files.internal("user/noavatar.png"));
	public Texture offline = new Texture(Gdx.files.internal("ui/close.png"));
	public Texture online = new Texture(Gdx.files.internal("ui/reduce.png"));

	boolean recherche;
	public List<String> contacts = new ArrayList<String>();
	private TextField search;

	private Stage stage;
	private PacketMessagerie packet;
	
	private boolean askForAvatar = false;

	public Messagerie() {
		packet = new PacketMessagerie();
		GrowingHack.instance.client.client.sendTCP(packet);
		
		this.setDimension((int) (Gdx.graphics.getWidth() / 1.5f), (int) (Gdx.graphics.getHeight() / 1.5f));
		this.search = new TextField(0, 0, this.width - 12, 32, "");
		this.stage = new Stage();
		this.stage.addListener(new InputListener() {
			public boolean keyTyped(InputEvent event, char character) {
				if (search.selected) {
					search.keyTyped(character);
				}
				return false;
			}
		});
		Gdx.input.setInputProcessor(this.stage);
		this.icon = new Texture(Gdx.files.internal("apps/messagerie.png"));
	}

	public void render(Batch batch, int mouseX, int mouseY) {
		if (search.selected) recherche = true;
		else
			recherche = false;

		this.search.width = 150;
		this.search.x = this.x + 32;
		this.search.y = Gdx.graphics.getHeight() - this.y - 58;

		batch.draw(this.background, this.x, Gdx.graphics.getHeight() - this.y - this.height + 2, this.width, this.height - 24);
		batch.draw(this.blue, this.x, Gdx.graphics.getHeight() - this.y - this.height + 2, this.width / 4, this.height - 24);

		if (contacts.size() > 0) {
			for (int c = 0; c < contacts.size(); c++) {
				String allContacts = contacts.get(c).toUpperCase();
				
				if (allContacts.length() > 12) {
					allContacts = contacts.get(c).toUpperCase().substring(0, 12) + "..";
				}

				Font.messagerieContact.draw(batch, allContacts, this.x + 70, Gdx.graphics.getHeight() - this.y - 125 - (c * 64));
				
				File cache = new File("cache/" + contacts.get(c) + ".jpg");
				
				if(cache.exists())
				{
					this.avatar = new Texture(Gdx.files.absolute(cache.getAbsolutePath()));
				}
				else if(!cache.exists() && !this.askForAvatar)
				{
					PacketUserImage packet = new PacketUserImage();
					packet.username = contacts.get(c);
					GrowingHack.instance.client.client.sendTCP(packet);
					this.askForAvatar = true;
				}
				
				batch.draw(this.avatar, this.x + 12, Gdx.graphics.getHeight() - this.y - 156 - (c * 64), 48, 48);

				/** Coucou Angelo, ici La Voix, ta mission si tu l'acceptes sera de faire un packet pour vérifier si le joueur est connecté ou non **/
				
				if (API.isPlayerConnected(allContacts)) {
					batch.draw(this.online, this.x + Font.getWidth(allContacts, Font.messagerieContact) + 78, Gdx.graphics.getHeight() - this.y - 138 - (c * 64), 12, 12);
				} else {
					batch.draw(this.offline, this.x + Font.getWidth(allContacts, Font.messagerieContact) + 78, Gdx.graphics.getHeight() - this.y - 138 - (c * 64), 12, 12);
				}
			}
		} else {
			Font.messagerieContact.draw(batch, "Aucun contact".toUpperCase(), this.x + 35, Gdx.graphics.getHeight() - this.y - 130);
		}
		this.search.draw(batch, mouseX, mouseY);
	}

	public String getAppName() {
		return "Messagerie";
	}

	@Override
	public String getAppID() 
	{
		return "messagerie";
	}
}
