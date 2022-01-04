package wolfirc;

public class IRCUser {

	protected String username;
	protected boolean op;

	public IRCUser(String _username) {
		if (_username.startsWith("@")) {
			_username = _username.substring(1);
			op = true;
		}
		username = _username;
	}

	public boolean isOp() {
		return op;
	}

	public String username() {
		return username;
	}
}
