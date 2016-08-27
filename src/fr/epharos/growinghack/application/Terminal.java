package fr.epharos.growinghack.application;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import fr.epharos.growinghack.GrowingHack;
import fr.epharos.growinghack.command.Command;
import fr.epharos.growinghack.os.OS;
import fr.epharos.growinghack.packets.PacketCommand;
import fr.epharos.growinghack.util.Font;

public class Terminal extends Application 
{
	public Texture background = new Texture(Gdx.files.internal("ui/background.png"));
	
	private static List<String> lines = new ArrayList<String>();
	private static String currentLine = "";
	
	private static int scrollLine = 0;
	private static int drawBetween = 0;
	
	public static boolean freeze = false;
	
	public Terminal()
	{
		Terminal.lines.clear();
		Terminal.addLines("Terminal de commande", "");
		Terminal.currentLine = "";
		this.setDimension(800, 563);
		this.minHeight = 563;
		this.minWidth = 800;
		this.x = Gdx.graphics.getWidth() / 2 - this.width / 2;
		this.y = Gdx.graphics.getHeight() / 2 - this.height / 2;
		this.x2 = this.x;
		this.y2 = this.y;
	}
	
	public static void addLines(String ... s1)
	{
		for(String s : s1)
		{
			Terminal.lines.add(s);
		}
	}
	
	public static void addChar(char key)
	{
		String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0122456789 ";
		
		for(int i = 0 ; i < allowedChars.length() ; i++)
		{
			if(key == allowedChars.charAt(i) && Terminal.currentLine.length() <= 48)
			{
				Terminal.currentLine += key;
			}
		}				
	}
	
	public static void setScrolling(int amount)
	{
		if(amount < 0)
		{
			Terminal.scrollLine = Terminal.drawBetween + 23;
			Terminal.scrollLine++;
			return;
		}
		
		if(amount > 0)
		{
			Terminal.scrollLine = Terminal.drawBetween;
			Terminal.scrollLine--;
		}
	}
	
	public void render(Batch batch, int mouseX, int mouseY) 
	{		
		batch.draw(this.background, this.x + 2, Gdx.graphics.getHeight() - this.y - this.height + 2, this.width - 4, this.height - 24);
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && !Terminal.freeze)
		{
			Terminal.addLines("Client." + GrowingHack.currentUser.username + " > " + Terminal.currentLine);
			
			if(Command.getCommand(Terminal.currentLine.split(" ")[0]) != null)
			{
				Command.getCommand(Terminal.currentLine.split(" ")[0]).execute(Terminal.currentLine.split(" "), true, false, 0);
			}
			else
			{
				Terminal.addLines("Commande non reconnue ...", "");
			}
			
			PacketCommand packet = new PacketCommand();
			packet.command = Terminal.currentLine;
			GrowingHack.instance.client.client.sendTCP(packet);
			
			Terminal.currentLine = "";
			Terminal.scrollLine = Terminal.lines.size();
			Terminal.drawBetween = Terminal.scrollLine - 23;
			Terminal.drawBetween = Terminal.lines.size() < 22 ? 0 : Terminal.lines.size() - 22;
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE))
		{
			Terminal.currentLine = Terminal.currentLine.substring(0, Terminal.currentLine.length() - 1);
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.PAGE_UP))
		{
			Terminal.scrollLine = Terminal.drawBetween;
			Terminal.scrollLine--;
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.PAGE_DOWN))
		{
			Terminal.scrollLine = Terminal.drawBetween + 23;
			Terminal.scrollLine++;
		}
		
		if(Terminal.scrollLine < 0)
		{
			Terminal.scrollLine = 0;
		}
		
		if(Terminal.scrollLine > Terminal.lines.size())
		{
			Terminal.scrollLine = Terminal.lines.size();
		}
		
		if(Terminal.drawBetween > Terminal.scrollLine)
		{
			Terminal.drawBetween = Terminal.scrollLine;
		}
		
		if(Terminal.drawBetween < Terminal.scrollLine - 22)
		{
			Terminal.drawBetween = Terminal.scrollLine - 22;
		}
		
		if(Terminal.drawBetween < 0)
		{
			Terminal.drawBetween = 0;
		}
	}
	
	public void drawForeground(Batch batch, int mouseX, int mouseY, OS os)
	{
		super.drawForeground(batch, mouseX, mouseY, os);
		
		int i = 0;
		
		if(!Terminal.lines.isEmpty())
		{
			for(i = 0 ; i < (Terminal.lines.size() < 22 ? Terminal.lines.size() : 22) ; i++)
			{
				Font.terminalWhite.draw(batch, Terminal.lines.get(Terminal.lines.size() - (Terminal.lines.size() < 22 ? Terminal.lines.size() : 22) + i - Terminal.drawBetween), this.x + 4, Gdx.graphics.getHeight() - this.y - 22 * i - 4 - 24);
			}
		}
		
		Font.terminalGreen.draw(batch, "Client." + GrowingHack.currentUser.username + " >", this.x + 4, Gdx.graphics.getHeight() - this.y - (i + 1) * 19 - (4 * (i + 1)) - 3 + 12);
		Font.terminalWhite.draw(batch, Terminal.currentLine, this.x + 4 + Font.getWidth("Client." + GrowingHack.currentUser.username + " > ", Font.terminalGreen), Gdx.graphics.getHeight() - this.y - (i + 1) * 19 - (4 * (i + 1)) - 3 + 12);
	}

	public String getAppName() 
	{
		return "Terminal de commande";
	}
	
	public boolean resizable()
	{
		return false;
	}

	public int getAppID() 
	{
		return 0;
	}
}
