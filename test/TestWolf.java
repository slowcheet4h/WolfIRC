package test;

import pisi.unitedmeows.yystal.utils.kThread;
import wolfirc.WolfIRC;

public class TestWolf {

	public static void main(String[] args) {
		WolfIRC wolfIRC = new WolfIRC("ezezez", "WolfIRC Bot");
		wolfIRC.connect("irc.freenode.net", 6667);

		kThread.sleep(100000);
	}
}
