package fr.growinghack.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

import fr.growinghack.util.Font;
import fr.growinghack.util.Timer;

public class TextField
{
	/** Solution possible : displayCursor < 1 displayCursor = 1 et refaire le display
	 * displayCursor > len(displayText) - 1 ; refaire le display et displayCursor = len(displayText) - 1
	 * 
	 * TODO !
	 */
	
	public int x, y, width, height, cursor, displayCursor, wwidth, wheight, wx, wy;
	public String text, displayText;
	public boolean selected = false, password = false, canSpace = false, canWrite = true;
	public static boolean canDelete = true, drawCursor = true;
	public char passwordChar = '*';
	
	public static final Texture texture = new Texture(Gdx.files.internal("ui/border.png"));
	public static final Texture cursorTexture = new Texture(Gdx.files.internal("ui/inside.png"));
	
	public BitmapFont theFont = Font.getFont(Font.usual, 24);
	
	public TextField(int x, int y, int width, int height, String text, boolean selected, boolean isPassword, boolean canSpace, boolean canWrite)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.setWritingBounds(x, y, width, height);
		this.text = text;
		this.selected = selected;
		this.password = isPassword;
		this.canSpace = canSpace;
		this.canWrite = canWrite;
		TextField.drawCursor = true;
		TextField.canDelete = true;
		this.cursor = this.text.length();
		
		new Timer("cursor" + (Math.floor(Math.random() * 10000)), 0.8f)
		{
			public void execute()
			{
				TextField.drawCursor = !TextField.drawCursor;
			}
		};
		
		new Timer("candelete" + (Math.floor(Math.random() * 10000)), 0.2f)
		{
			public void execute()
			{
				if(!TextField.canDelete)
				{
					TextField.canDelete = true;
				}
			}
		};
	}
	
	public TextField(int x, int y, int width, int height, String text)
	{
		this(x, y, width, height, text, false, false, false, true);
	}
	
	public TextField setSelected()
	{
		this.selected = true;
		return this;
	}
	
	public TextField setPassword(char c)
	{
		this.password = true;
		this.passwordChar = c;
		return this;
	}
	
	public TextField canSpace()
	{
		this.canSpace = true;
		return this;
	}
	
	public TextField setFont(BitmapFont font)
	{
		this.theFont = font;
		return this;
	}
	
	public TextField setWritingBounds(int x, int y, int width, int height)
	{
		this.wx = x;
		this.wy = y;
		this.wwidth = width;
		this.wheight = height;
		return this;
	}
	
	public void drawBackground(Batch batch, int mouseX, int mouseY)
	{
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && this.isTextFieldOver(mouseX, mouseY) && !this.selected)
		{
			this.selected = true;
		}
		else if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !this.isTextFieldOver(mouseX, mouseY))
		{
			this.selected = false;
		}
		
		Color c = batch.getColor();
		batch.setColor(c.r, c.g, c.b, 0.8f);
		batch.draw(TextBox.texture, this.x, this.y, this.width, this.height);
		batch.setColor(c);
	}
	
	public void inputs(int mouseX, int mouseY)
	{
		if(Gdx.input.isKeyPressed(Input.Keys.DEL))
		{
			if(TextField.canDelete && this.selected)
			{
				if(this.text.length() > 0)
				{
					try
					{	this.text = this.text.substring(0, this.cursor - 1).concat(this.text.substring(this.cursor, this.text.length()));
						this.cursor--;
						TextField.canDelete = false;
					}
					catch(Exception e) { }
				}
			}
		}
		
		if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {

			if (TextField.canDelete && this.selected) {
				if (text.length() > 0) {

					text = text.substring(0, this.cursor);
					cursor = 0;
					TextField.canDelete = false;

				}
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.LEFT))
		{
			if(this.cursor > 0)
			{
				this.cursor--;
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.RIGHT))
		{
			if(this.cursor < this.text.length())
			{
				this.cursor++;
			}
		}
		
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && this.isTextFieldOver(Gdx.input.getX(), Gdx.input.getY()))
		{
			int mouseXOffSet = Gdx.input.getX() - this.x;
			
			for(int i = 0 ; i < this.displayText.length() ; i++)
			{
				if(Font.getWidth(this.displayText.substring(0, i), this.theFont) >= mouseXOffSet)
				{
					this.cursor = i;
					
					if(this.cursor < 0)
					{
						this.cursor = 0;
					}
					
					break;
				}
			}
		}
	}
	
	public void draw(Batch batch, int mouseX, int mouseY)
	{
		this.drawBackground(batch, mouseX, mouseY);
		this.inputs(mouseX, mouseY);
		
		this.updateDisplayText();
		
//		if(this.selected)
//		{
//			if(TextField.drawCursor)
//			{
//				
//				
//				for(int i = 0 ; i < Font.getHeight("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", this.theFont) + 5 ; i++)
//				{
//					batch.draw(TextField.cursorTexture, x + 2 + Font.getWidth(this.displayText.substring(0, this.cursor), this.theFont), this.y + 5 + i);
//				}
//			}
//		}
		
		Rectangle r1 = new Rectangle();
		Rectangle toDrawIn = new Rectangle(this.wx, this.wy, this.wwidth, this.wheight);
		Stage stage = new Stage();
		ScissorStack.calculateScissors(stage.getCamera(), batch.getTransformMatrix(), toDrawIn, r1);
		ScissorStack.pushScissors(r1);
		
		this.theFont.draw(batch, this.displayText, this.x + 4, this.y + this.height / 2 + (this.theFont.getBounds(this.text).height / 2));
		
		batch.flush();
		ScissorStack.popScissors();
	}
	
	public void updateDisplayText()
	{
		float fullSize = Font.getWidth(this.text, this.theFont);
		
		String temp = "";
		int n = 0;
		
		if(fullSize > this.wwidth)
		{
			for(int i = this.text.length() - 1 ; i > 0 ; i--)
			{
				if(Font.getWidth(temp, this.theFont) + Font.getWidth(String.valueOf(this.text.charAt(i)), this.theFont) < this.wwidth)
				{
					n = i + 1;
					temp = temp.concat(String.valueOf(this.text.charAt(i)));
				}
				else
				{
					break;
				}
			}
		
			temp = "";
		
			for(int a = n ; a < this.text.length() ; a++)
			{
				temp = temp.concat(String.valueOf(this.text.charAt(a)));
			}
			
			this.displayText = temp;
		}
		else
		{
			this.displayText = this.text;
		}
		
		if(password)
		{
			int a = this.displayText.length();
			this.displayText = "";
			
			for(int i = 0 ; i < a ; i++)
			{
				this.displayText = this.displayText.concat(String.valueOf(this.passwordChar));
			}
		}
	}
	
	public boolean isTextFieldOver(int mouseX, int mouseY)
	{
		mouseY = Gdx.graphics.getHeight() - mouseY;
		return (mouseX >= this.x && mouseX <= this.x + this.width) && (mouseY >= this.y && mouseY <= this.y + this.height);
	}

	public void keyTyped(char key) 
	{		
		String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.+-&�(-�_��)=^$�*!:;,~#{[|`^@]}��*��%��/?\"\'";
		if (this.canSpace) allowedChars = allowedChars + " ";
		
		for(int i = 0 ; i < allowedChars.length() ; i++)
		{
			if(key == allowedChars.charAt(i))
			{
				if(this.selected && this.canWrite)
				{
					if(this.text != null && !this.text.equals(""))
					{
						this.text = this.text.substring(0, this.cursor).concat(String.valueOf(key)).concat(this.text.substring(this.cursor, this.text.length()));
						this.cursor++;
					}
					else
					{
						this.text += key;
						this.cursor++;
					}
				}
			}
		}
	}
}
