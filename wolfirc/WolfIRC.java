package wolfirc;

import pisi.unitedmeows.yystal.clazz.event;
import pisi.unitedmeows.yystal.clazz.prop;
import pisi.unitedmeows.yystal.networking.IPAddress;
import pisi.unitedmeows.yystal.networking.client.YTcpClient;
import pisi.unitedmeows.yystal.networking.client.extension.impl.CTcpLineRead;
import pisi.unitedmeows.yystal.networking.events.CDataReceivedEvent;
import wolfirc.events.*;
import wolfirc.lex.Lex;
import wolfirc.lex.impl.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;

public class WolfIRC {

	protected YTcpClient client;
	protected String host;
	protected int port;
	protected String username;
	protected String userInfo;
	protected static List<Lex> lexes /* all my exes live in texas */ = new ArrayList<Lex>();


	public event<WOnPing> onPingEvent = new event<>();
	public event<WOnMotdEnd> onMotdEndEvent = new event<>();
	public event<WOnRawData> onRawDataEvent = new event<>();
	public event<WOnRoomJoin> onRoomJoin = new event<>();
	public event<WOnChannelMessage> onChannelMessage = new event<>();

	public final HashMap<String, IRCChannel> joinedRooms;

	public prop<Boolean> hasMotdEnd = new prop<Boolean>(false) {
		@Deprecated
		@Override
		public void set(Boolean newValue) {
			value = newValue;
		}

		@Override
		public Boolean get() {
			return value;
		}
	};

	static {
		lexes.add(new PingLex());
		lexes.add(new MotdEndLex());
		lexes.add(new RoomJoinLex());
		lexes.add(new ChannelNamesLex());
		lexes.add(new ChannelMessageLex());
	}

	public WolfIRC(final String _username, final String _userInfo) {
		client = new YTcpClient();
		username = _username;
		userInfo = _userInfo;
		joinedRooms = new HashMap<>();
	}

	public void connect(String _host, int _port) {
		port = _port;
		host = _host;

		/* sets the tcp client for reading raw string lines */
		client.registerExtension(new CTcpLineRead());

		/* tries to connect the irc server */
		client.connect(IPAddress.parse(_host), port);

		/* listens for data */
		client.dataReceivedEvent.bind(new CDataReceivedEvent() {
			@Override
			public void onDataReceived(byte[] bytes) {
				final String received = new String(bytes);
				for (Lex lex : lexes) {
					Matcher matcher = lex.regex().matcher(received);
					if (matcher.find()) {
						lex.onDataReceive(received, matcher, instance());
					}
				}
				onRawDataEvent.fire(received);
			}
		});

		/* sends user info */
		sendRawLine("NICK " + username);
		sendRawLine("USER " + username + " 8 * :" + userInfo);
		onMotdEndEvent.bind(new WOnMotdEnd() {
			@Override
			public void onMotdEnd() {
				joinRoom("freenode");
			}
		});
	}



	public boolean isConnected() {
		return client.isConnected();
	}

	public void sendRaw(String data) {
		client.send(data.getBytes(StandardCharsets.UTF_8));
	}

	public void joinRoom(String room) {
		if (room.startsWith("#"))
			room = room.substring(1);

		sendRawLine("JOIN #" + room);
	}

	public void sendRawLine(String data) {
		client.send((data + "\n").getBytes(StandardCharsets.UTF_8));
	}

	protected WolfIRC instance() {
		return this;
	}
}
