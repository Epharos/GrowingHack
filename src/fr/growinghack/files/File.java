package fr.growinghack.files;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

import fr.growinghack.GrowingHack;
import fr.growinghack.icon.Icon;
import fr.growinghack.util.Font;

public abstract class File 
{
	public ArrayList<String> content = new ArrayList<String>();
	public String content2, name;
	public Icon icon;
	public int i, j;
	
	public abstract String getExtention();
	
	public abstract void open();
	
	public void draw(Batch batch, int mouseX, int mouseY)
	{
		int textureWidth = this.icon.getTexture().getWidth();
		int textureHeight = this.icon.getTexture().getHeight();
		float k = 64.0f / Math.max(textureWidth, textureHeight);
		float dy = Gdx.graphics.getHeight() - 85 - this.j;
		
		batch.draw(this.icon.getTexture(), i + k * 64, dy + k * 64, k * textureWidth, k * textureHeight);
		
		Rectangle r1 = new Rectangle();
		Rectangle toDrawIn = new Rectangle(this.i, 0, 104, Integer.MAX_VALUE);
		Stage stage = new Stage();
		ScissorStack.calculateScissors(stage.getCamera(), batch.getTransformMatrix(), toDrawIn, r1);
		ScissorStack.pushScissors(r1);
		Font.appName.draw(batch, this.name, i, dy + 16);
		batch.flush();
		ScissorStack.popScissors();
	}
}
