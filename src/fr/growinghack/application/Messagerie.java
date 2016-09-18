package fr.growinghack.application;

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
import fr.growinghack.server.API;
import fr.growinghack.ui.TextField;
import fr.growinghack.util.Font;

public class Messagerie extends Application {

	public Texture background = new Texture(Gdx.files.internal("ui/white.png"));
	public Texture logo = new Texture(Gdx.files.internal("apps/messagerie.png"));
	public Texture blue = new Texture(Gdx.files.internal("ui/backgroundmessagerie.png"));
	public Texture hackerLogo = new Texture(Gdx.files.internal("ui/hacker.png"));
	public Texture offline = new Texture(Gdx.files.internal("ui/close.png"));
	public Texture online = new Texture(Gdx.files.internal("ui/reduce.png"));

	boolean recherche;
	public List<String> contacts = new ArrayList<String>();
	private TextField search;

	private Stage stage;
	private PacketMessagerie packet;

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
				
				if (allContacts.length() > 8) {
					allContacts = contacts.get(c).toUpperCase().substring(0, 8) + "..";
				}

				Font.messagerieContact.draw(batch, allContacts, this.x + 70, Gdx.graphics.getHeight() - this.y - 125 - (c * 64));
				batch.draw(this.hackerLogo, this.x, Gdx.graphics.getHeight() - this.y - 160 - (c * 64), 72, 72);

				if (API.isPlayerConnected(allContacts)) {
					batch.draw(this.online, this.x + 180, Gdx.graphics.getHeight() - this.y - 138 - (c * 64), 12, 12);
				} else {
					batch.draw(this.offline, this.x + 180, Gdx.graphics.getHeight() - this.y - 138 - (c * 64), 12, 12);
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
	public int getAppID() {
		return 0;
	}
}
