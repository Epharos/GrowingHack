package fr.growinghack.icon;

import fr.growinghack.application.Application;

public class Note extends Icon {

	public String getAppName() {
		return "Note";
	}

	public String getAppIcon() {
		return "note";
	}

	public Application openApp() {
		return new fr.growinghack.application.Note();
	}

	public int getAppID() {
		return 0;
	}

}
