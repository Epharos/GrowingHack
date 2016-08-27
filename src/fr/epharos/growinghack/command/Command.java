package fr.epharos.growinghack.command;

import fr.epharos.growinghack.application.Terminal;

public abstract class Command 
{
	
	/** @TODO A retravailler !
	 * Aucune documentation
	 */
	
	public String command = "";
	public boolean client = false, server = false;
	
	public static Command[] commands = new Command[256];
	
	public static final Command info = new CommandInfo("about", true, true);
	public static final Command connected = new CommandConnected("connected", false, true);
	public static final Command getIP = new CommandIP("ip", true, false);
	
	public abstract void execute(String[] args, boolean client, boolean server, int i);
	
	public Command(String name, boolean c, boolean s)
	{
		this.command = name;
		this.client = c;
		this.server = s;
		
		for(int i = 0 ; i < Command.commands.length ; i++)
		{
			if(Command.commands[i] == null)
			{
				Command.commands[i] = this;
				return;
			}
		}
	}
	
	public static void executeClient(String[] args, int i)
	{
		if(Command.getCommand(args[0]) != null)
		{
			if(Command.getCommand(args[0]).client)
			{
				Terminal.freeze = true;
				Command.getCommand(args[0]).execute(args, true, false, i);
				Terminal.freeze = false;
			}
		}
	}
	
	public static void executeServer(String[] args, int i)
	{
		if(Command.getCommand(args[0]) != null)
		{
			if(Command.getCommand(args[0]).server)
			{
				Command.getCommand(args[0]).execute(args, false, true, i);
			}
		}
	}
	
	public static Command getCommand(String s)
	{
		for(Command c : Command.commands)
		{
			if(c != null)
			{
				if(c.command.equals(s))
				{
					return c;
				}
			}
		}
		
		return null;
	}
}
