package wolfirc;

import java.util.ArrayList;
import java.util.List;

public class IRCChannel {

	protected String name;
	private List<IRCUser> users;

	public IRCChannel(String _name) {
		name = _name;
		users = new ArrayList<>();
	}

	public List<IRCUser> users() {
		return users;
	}

	public String name() {
		return name;
	}
}
