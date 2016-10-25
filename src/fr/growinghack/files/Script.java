package fr.growinghack.files;

import fr.growinghack.application.Terminal;

public class Script extends File
{
	public String getExtention() 
	{
		return "scr";
	}

	public void open() 
	{
		new Terminal();
//		
//		for(String line : this.content)
//		{
//			if(Command.getCommand(line.split(" ")[0]) != null)
//			{
//				Command.getCommand(line.split(" ")[0]).execute(line.split(" "), true, false, 0);
//			}
//			
//			PacketCommand packet = new PacketCommand();
//			packet.command = line;
//			GrowingHack.instance.client.client.sendTCP(packet);
//		}
	}
}
