package fr.growinghack.packets;

import java.util.List;

public class PacketMessagerie extends Packet {

	public List<String> contacts;
	
	@Override
	public void handlePacket(Handler handler, int connexionID) {
		handler.handleContact(this, connexionID);
	}
}
