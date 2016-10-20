package fr.growinghack.application;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import fr.growinghack.os.OS;
import fr.growinghack.ui.Button;
import fr.growinghack.ui.MulticolorLine;
import fr.growinghack.util.Font;

public class Note extends Application {
	public Texture background = new Texture(Gdx.files.internal("ui/backgroundterminal.png"));
	public Texture cursor = new Texture(Gdx.files.internal("ui/white.png"));

	private static List<MulticolorLine> lines = new ArrayList<MulticolorLine>();
	private static String currentLine = "";

	private static int scrollLine = 0;
	private static int drawBetween = 0;

	public static boolean freeze = false;

	public Note() {
		Note.lines.clear();
		Note.currentLine = "";
		this.setDimension(800, 563);
		this.minHeight = 563;
		this.minWidth = 800;
		this.x = Gdx.graphics.getWidth() / 2 - this.width / 2;
		this.y = Gdx.graphics.getHeight() / 2 - this.height / 2;
		this.x2 = this.x;
		this.y2 = this.y;
	}

	public static void addLines(MulticolorLine... s1) {
		for (MulticolorLine s : s1) {
			Note.lines.add(s);
		}
	}

	public static void addLines(String... s1) {
		for (String s : s1) {
			Note.lines.add(new MulticolorLine(Font.terminal, Color.WHITE, s));
		}
	}

	public static void addChar(char key) {
		String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.+-&é(-è_çà)=^$ù*!:;,~#{[|`^@]}¤ù*¨£%µ§/?\"\' ";

		for (int i = 0; i < allowedChars.length(); i++) {
			if (key == allowedChars.charAt(i) && Note.currentLine.length() <= 98) {
				Note.currentLine += key;
			}
		}
	}

	public static void setScrolling(int amount) {
		if (amount < 0) {
			Note.scrollLine = Note.drawBetween + 23;
			Note.scrollLine++;
			return;
		}

		if (amount > 0) {
			Note.scrollLine = Note.drawBetween;
			Note.scrollLine--;
		}
	}

	public void drawBackground(Batch batch, int mouseX, int mouseY) {
		batch.draw(Button.inside, this.x, Gdx.graphics.getHeight() - this.y - 22, this.width, 24);
		batch.draw(this.background, this.x, Gdx.graphics.getHeight() - this.y - this.height + 2, this.width, this.height - 24);
	}

	public void render(Batch batch, int mouseX, int mouseY) {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			Note.addLines(new MulticolorLine(Font.terminal, 
					new Color[] {Color.WHITE }, Note.currentLine));
			
			Note.currentLine = "";
			Note.scrollLine = Note.lines.size();
			Note.drawBetween = Note.scrollLine - 23;
			Note.drawBetween = Note.lines.size() < 22 ? 0 : Note.lines.size() - 22;
		}

		if (Note.currentLine.length() > 97) {
			Note.addLines(new MulticolorLine(Font.terminal, new Color[] { Color.WHITE }, Note.currentLine));
			Note.currentLine = "";
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
			if (Note.currentLine.length() != 0) {
				Note.currentLine = Note.currentLine.substring(0, Note.currentLine.length() - 1);
			}
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT)) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
				Note.lines.clear();
			}
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.PAGE_UP)) {
			Note.scrollLine = Note.drawBetween;
			Note.scrollLine--;
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.PAGE_DOWN)) {
			Note.scrollLine = Note.drawBetween + 23;
			Note.scrollLine++;
		}

		if (Note.scrollLine < 0) {
			Note.scrollLine = 0;
		}

		if (Note.scrollLine > Note.lines.size()) {
			Note.scrollLine = Note.lines.size();
		}

		if (Note.drawBetween > Note.scrollLine) {
			Note.drawBetween = Note.scrollLine;
		}

		if (Note.drawBetween < Note.scrollLine - 22) {
			Note.drawBetween = Note.scrollLine - 22;
		}

		if (Note.drawBetween < 0) {
			Note.drawBetween = 0;
		}
	}

	public void drawForeground(Batch batch, int mouseX, int mouseY, OS os) {
		super.drawForeground(batch, mouseX, mouseY, os);

		int i = 0;

		if (!Note.lines.isEmpty()) {
			for (i = 0; i < (Note.lines.size() < 21 ? Note.lines.size() : 21); i++) {
				Note.lines.get(Note.lines.size() - (Note.lines.size() < 21 ? Note.lines.size() : 21) + i - Note.drawBetween).drawMulticolor(batch, this.x + 4, Gdx.graphics.getHeight() - this.y - 15 * (i + 1) - 4 * (i + 1) - 7);
			}
		}

		Font.drawMulticolor(Font.terminal, batch, this.x + 4, Gdx.graphics.getHeight() - this.y - (i + 2) * 15 - (4 * (i + 2)) + 10, new Color[] { Color.WHITE }, Note.currentLine);
	}

	public String getAppName() {
		return "Note";
	}

	public int getAppID() {
		return 0;
	}
}
