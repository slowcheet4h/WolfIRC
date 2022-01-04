package wolfirc;

import pisi.unitedmeows.yystal.clazz.event;
import pisi.unitedmeows.yystal.clazz.prop;
import pisi.unitedmeows.yystal.networking.IPAddress;
import pisi.unitedmeows.yystal.networking.client.YTcpClient;
import pisi.unitedmeows.yystal.networking.client.extension.impl.CTcpLineRead;
import pisi.unitedmeows.yystal.networking.events.CDataReceivedEvent;
import pisi.unitedmeows.yystal.parallel.Async;
import pisi.unitedmeows.yystal.utils.kThread;
import wolfirc.events.WOnMotdEnd;
import wolfirc.events.WOnPing;
import wolfirc.events.WOnRawData;
import wolfirc.lex.Lex;
import wolfirc.lex.impl.MotdEndLex;
import wolfirc.lex.impl.PingLex;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
	}

	public WolfIRC(final String _username, final String _userInfo) {
		client = new YTcpClient();
		username = _username;
		userInfo = _userInfo;
	}

	public void connect(String _host, int _port) {
		port = _port;
		host = _host;
		client.registerExtension(new CTcpLineRead());
		client.connect(IPAddress.parse(_host), port);
		client.dataReceivedEvent.bind(new CDataReceivedEvent() {
			@Override
			public void onDataReceived(byte[] bytes) {
				final String received = new String(bytes);
				for (Lex lex : lexes) {
					Matcher matcher = lex.regex().matcher(received);
					if (matcher.matches()) {
						lex.onDataReceive(received, matcher, instance());
					}
				}
				System.out.println(received);
			}
		});

		Async.async_w(() -> {
			System.out.println("trying to join");
			client.send(("NICK " + username + "\n").getBytes(StandardCharsets.UTF_8));
			client.send(("USER " + username + " 8 * :" + userInfo + "\n").getBytes(StandardCharsets.UTF_8));

		}, 1500);
	}


	public boolean isConnected() {
		return client.isConnected();
	}

	public void sendRaw(String data) {
		client.send(data.getBytes(StandardCharsets.UTF_8));
	}

	public void sendRawLine(String data) {
		client.send((data + "\n").getBytes(StandardCharsets.UTF_8));
	}

	protected WolfIRC instance() {
		return this;
	}
}
