package fr.growinghack.files;

import fr.growinghack.GrowingHack;
import fr.growinghack.application.Note;
import fr.growinghack.icon.Icon;

public class Text extends File
{	
	public Text(String content)
	{
		this.icon = new Icon("apps/note.png");
		this.content = content;
	}
	
	public String getExtention() 
	{
		return "txt";
	}

	public void open() 
	{
		Note note = new Note(content);
		GrowingHack.currentOS.applications.add(note);
	}
}
