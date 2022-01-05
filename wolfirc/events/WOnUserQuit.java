package wolfirc.events;

import pisi.unitedmeows.yystal.clazz.delegate;

public interface WOnUserQuit extends delegate {
	public void onUserQuit(String username, String reason);
}
