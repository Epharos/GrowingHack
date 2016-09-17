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
	public static File dir;
	
	public FileLoader()
	{
		FileLoader.dir = new File("files.txt");
	}
	
	public static void load()
	{
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
		
		Folder folder = new Folder();
		boolean isFolderOpen = false;
		
		for(String line : content)
		{
			if(line.startsWith("<folder") && line.endsWith(">"))
			{
				folder = new Folder();
				isFolderOpen = true;
				
				String[] values = line.substring(7, line.length() - 2).split(";");
				
				if(values.length > 0)
				{
					if(values[0].startsWith("name"))
					{
						folder.name = values[0].split(":")[1];
					}
				}
			}
			
			if(line.equals("</folder>"))
			{
				GrowingHack.currentOS.folders.add(folder);
				isFolderOpen = false;
			}
			
			if(line.startsWith("<file") && line.endsWith(">") && isFolderOpen)
			{
				String[] values = line.substring(5, line.length() - 2).split(";");
				
				fr.growinghack.files.File file = null;
				
				if(values.length > 0)
				{
					for(String value : values)
					{
						String field = value.split(":")[0];
						String data = value.split(":")[1];
						
						if(field.equals("extention"))
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
						
						folder.files.add(file);
					}
				}
			}
			
			if(line.startsWith("<specialfile") && line.endsWith(">") && isFolderOpen)
			{
				String[] values = line.substring(12, line.length() - 2).split(";");
				
				SpecialFile file = new SpecialFile();
				
				if(values.length > 0)
				{
					for(String value : values)
					{
						String field = value.split(":")[0];
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
						
						folder.files.add(file);
					}
				}
			}
		}
	}
}
