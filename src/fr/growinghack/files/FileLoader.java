package fr.growinghack.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import fr.growinghack.GrowingHack;
import fr.growinghack.application.Messagerie;
import fr.growinghack.application.Terminal;
import fr.growinghack.application.WebBrowser;

public class FileLoader 
{	
	public static void load()
	{
		File dir = new File("files.txt");
		
		if(!dir.exists())
		{
			try
			{
				dir.createNewFile();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		List<String> content = new ArrayList<String>();
		
		try
		{
			InputStream is = new FileInputStream(dir);
			
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			
			try 
			{
				while((line = br.readLine()) != null)
				{
					content.add(line);
				}
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		List<Folder> folders = new ArrayList<Folder>();
		
		for(String line : content)
		{
			line = line.replace("\t", "");
			
			if(line.startsWith("<folder") && line.endsWith(">"))
			{
				folders.add(new Folder());
				
				String[] values = line.substring(7, line.length() - 1).split(";");
				
				if(values.length > 0)
				{
					if(values[0].contains("name"))
					{
						folders.get(folders.size() - 1).name = values[0].split(":")[1];
					}
				}
			}
			
			if(line.equals("</folder>"))
			{
				if(folders.size() == 1)
				{
					GrowingHack.currentOS.files.add(folders.get(0));
					folders.remove(0);
				}
				else
				{
					folders.get(folders.size() - 2).files.add(folders.get(folders.size() - 1));
					folders.remove(folders.size() - 1);
				}
				
			}
			
			if(line.startsWith("<file") && line.endsWith(">"))
			{				
				String[] values = line.substring(5, line.length() - 1).split(";");
				
				fr.growinghack.files.File file = null;
				
				if(values.length > 0)
				{
					for(String value : values)
					{
						String field = value.split(":")[0];
						field = field.replace(" ", "");
						String data = value.split(":")[1];
						
						if(field.equals("type"))
						{
							if(data.equals("txt"))
							{
								file = new Text();
							}
						}
						
						if(field.equals("name"))
						{
							file.name = data;
						}
						
						if(field.equals("content"))
						{
							for(String cline : data.split("/nl"))
							{
								file.content.add(cline);
							}
						}
						
					}
				}
				
				folders.get(folders.size() - 1).files.add(file);
			}
			
			if(line.startsWith("<specialfile") && line.endsWith(">"))
			{
				String[] values = line.substring(12, line.length() - 2).split(";");
				
				SpecialFile file = new SpecialFile();
				
				if(values.length > 0)
				{
					for(String value : values)
					{
						String field = value.split(":")[0];
						field = field.replace(" ", "");
						String data = value.split(":")[1];
						
						if(field.equals("open"))
						{
							if(data.equals("terminal"))
							{
								file.toOpen = Terminal.class;
							}
							
							if(data.equals("webbrowser"))
							{
								file.toOpen = WebBrowser.class;
							}
							
							if(data.equals("messagerie"))
							{
								file.toOpen = Messagerie.class;
							}
						}
						
						if(field.equals("name"))
						{
							file.name = data;
						}

					}
				}
				
				folders.get(folders.size() - 1).files.add(file);
			}
		}
	}
	
	public static void save()
	{
		
	}
	
	public static void print(List<fr.growinghack.files.File> files, int num)
	{
		for(fr.growinghack.files.File f : files)
		{
			if(f instanceof Folder)
			{
				System.out.println(FileLoader.tabs(num) + "Folder > " + f.name);
				FileLoader.print(((Folder) f).files, num + 1);
			}
			else
			{
				System.out.println(FileLoader.tabs(num) + "File > " + f.name);
			}
		}
	}
	
	public static String tabs(int num)
	{
		String toReturn = "";
		
		for(int i = 0 ; i < num ; i++)
		{
			toReturn = toReturn.concat("\t");
		}
		
		return toReturn;
	}
	
	public static void pr(Object o)
	{
		System.out.println(o);
	}
}
