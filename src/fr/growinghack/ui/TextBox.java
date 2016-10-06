package fr.growinghack.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

import fr.growinghack.util.Font;
import fr.growinghack.util.Timer;

public class TextBox 
{
	public int x, y, width, height, cursor, cursorOnScreen;
	
	public String text, shownText;
	
	public boolean selected, isPassword, canSpace, canWrite;
	public static boolean drawCursor;
	
	public static final Texture texture = new Texture(Gdx.files.internal("ui/border.png"));
	public static final Texture cursorTexture = new Texture(Gdx.files.internal("ui/inside.png"));
	
	public static boolean canDelete = true;
	
	public TextBox(int x, int y, int width, int height, String text, boolean selected, boolean isPassword, boolean canSpace, boolean canWrite)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		this.selected = selected;
		this.isPassword = isPassword;
		this.canSpace = canSpace;
		this.canWrite = canWrite;
		TextBox.drawCursor = true;
		TextBox.canDelete = true;
		
		new Timer("cursor", 0.8f)
		{
			public void execute()
			{
				TextBox.drawCursor = !TextBox.drawCursor;
			}
		};
		
		new Timer("candelete", 0.2f)
		{
			public void execute()
			{
				if(!TextBox.canDelete)
				{
					TextBox.canDelete = true;
				}
			}
		};
	}
	
	public TextBox(int x, int y, int width, int height, String text)
	{
		this(x, y, width, height, text, false, false, false, true);
	}
	
	public TextBox setSelected()
	{
		this.selected = true;
		return this;
	}
	
	public TextBox setPassword()
	{
		this.isPassword = true;
		return this;
	}
	
	public TextBox canSpace()
	{
		this.canSpace = true;
		return this;
	}
	
	public void draw(Batch batch, int mouseX, int mouseY)
	{
		if(this.cursor > this.cursorOnScreen)
		{
			this.cursorOnScreen = this.shownText.length();
		}
		
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && this.isTextBoxOver(mouseX, mouseY) && !this.selected)
		{
			this.selected = true;
		}
		else if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !this.isTextBoxOver(mouseX, mouseY))
		{
			this.selected = false;
		}
		
		this.updateOnScreenText();
		
		Color c = batch.getColor();
		batch.setColor(c.r, c.g, c.b, 0.8f);
		batch.draw(TextBox.texture, this.x, this.y, this.width, this.height);
		batch.setColor(c);
		
		if(this.selected)
		{
			if(TextBox.drawCursor)
			{
				System.out.println(this.cursorOnScreen + "/" + this.shownText.length());
				batch.draw(TextBox.cursorTexture, x + 4 + Font.getWidth(this.shownText.substring(0, this.cursorOnScreen), Font.getFont(Font.usual, 24)), this.y + 5, 1, 24);
			}
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.DEL))
		{
			if(TextBox.canDelete && this.selected)
			{
				if(this.text.length() > 0)
				{
					this.text = this.text.substring(0, this.cursor - 1).concat(this.text.substring(this.cursor, this.text.length()));
					this.cursor--;
					TextBox.canDelete = false;
				}
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.LEFT))
		{
			if(this.cursor > 0)
			{
				this.cursor--;
				this.cursorOnScreen--;
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.RIGHT))
		{
			if(this.cursor < this.shownText.length())
			{
				this.cursor++;
				this.cursorOnScreen++;
			}
		}
		
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && this.isTextBoxOver(Gdx.input.getX(), Gdx.input.getY()))
		{
			int mouseXOffSet = Gdx.input.getX() - this.x;
			
			for(int i = 0 ; i < this.shownText.length() ; i++)
			{
				if(Font.getWidth(this.shownText.substring(0, i), Font.getFont(Font.usual, 24)) >= mouseXOffSet)
				{
					this.cursor = i - 1;
					
					if(this.cursor < 0)
					{
						this.cursor = 0;
						this.cursorOnScreen = 0;
					}
					
					if(this.cursorOnScreen < 0)
					{
						this.cursorOnScreen = 0;
					}
					
					break;
				}
			}
			
			if(mouseXOffSet > Font.getWidth(this.shownText, Font.getFont(Font.usual, 24)))
			{
				this.cursor = this.shownText.length();
			}
		}
		
		Rectangle r1 = new Rectangle();
		Rectangle toDrawIn = new Rectangle(this.x, this.y, this.width, this.height);
		Stage stage = new Stage();
		ScissorStack.calculateScissors(stage.getCamera(), batch.getTransformMatrix(), toDrawIn, r1);
		ScissorStack.pushScissors(r1);
		Font.getFont(Font.usual, 24).draw(batch, this.shownText, this.x + 4, this.y + this.height / 2 + (Font.getFont(Font.usual, 24).getBounds(this.text).height / 2));
		batch.flush();
		ScissorStack.popScissors();
	}
	
	public void updateOnScreenText()
	{
		float fullSize = Font.getWidth(this.text, Font.getFont(Font.usual, 24));
		
		String temp = "";
		int n = 0;
		
		if(fullSize > this.width)
		{
			for(int i = this.text.length() - 1 ; i > 0 ; i--)
			{
				if(Font.getWidth(temp, Font.getFont(Font.usual, 24)) + Font.getWidth(String.valueOf(this.text.charAt(i)), Font.getFont(Font.usual, 24)) < this.width - 8)
				{
					n = i;
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
			
			this.shownText = temp;
		}
		else
		{
			this.shownText = this.text;
		}
		
		if(this.isPassword)
		{
			int a = this.shownText.length();
			this.shownText = "";
			
			for(int i = 0 ; i < a ; i++)
			{
				this.shownText = this.shownText.concat("*");
			}
		}
		
		try
		{
			if(this.cursorOnScreen > this.shownText.length())
			{
				this.cursorOnScreen = this.shownText.length();
			}
		}
		catch(Exception e)
		{
			
		}
	}
	
	public boolean isTextBoxOver(int mouseX, int mouseY)
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
						this.cursorOnScreen++;
					}
					else
					{
						this.text += key;
						this.cursor++;
						this.cursorOnScreen++;
					}
				}
			}
		}
	}
}
