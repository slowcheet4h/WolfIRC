package wolfirc.lex.impl;

import wolfirc.IRCChannel;
import wolfirc.WolfIRC;
import wolfirc.lex.Lex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoomJoinLex extends Lex {

	public RoomJoinLex() {
		super(Pattern.compile("^(:)[^ :]+![^ :]+ JOIN :(\\#[^ ]+)"));
	}

	@Override
	public void onDataReceive(String data, Matcher matcher, WolfIRC client) {
		String roomName = matcher.group(2);

		if (roomName.startsWith("#"))
			roomName = roomName.substring(1);

		client.joinedRooms.put(roomName, new IRCChannel(roomName));
		client.onChannelJoinEvent.fire(roomName);
	}
}
