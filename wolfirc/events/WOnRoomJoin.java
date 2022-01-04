package wolfirc.events;

import pisi.unitedmeows.yystal.clazz.delegate;

public interface WOnRoomJoin extends delegate {
	public void onRoomJoin(final String room);
}
