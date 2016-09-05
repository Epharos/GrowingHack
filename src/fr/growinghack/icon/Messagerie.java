package fr.growinghack.icon;

import fr.growinghack.application.Application;

public class Messagerie extends Icon {
	
	public Messagerie() {
	}
	
	@Override
	public String getAppName() {
		return "Messagerie";
	}

	@Override
	public String getAppIcon() {
		return "messagerie";
	}

	@Override
	public Application openApp() {
		return new fr.growinghack.application.Messagerie();
	}

	@Override
	public int getAppID() {
		return 0;
	}

}
