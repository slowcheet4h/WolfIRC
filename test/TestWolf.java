package test;

import pisi.unitedmeows.yystal.utils.kThread;
import wolfirc.IRCChannel;
import wolfirc.IRCUser;
import wolfirc.WolfIRC;
import wolfirc.events.WOnChannelMessage;

public class TestWolf {

    public static void main(String[] args) {
        WolfIRC wolfIRC = new WolfIRC("ezezez", "WolfIRC Bot");
        wolfIRC.connect("irc.freenode.net", 6667);

        wolfIRC.onChannelMessage.bind(new WOnChannelMessage() {
            @Override
            public void onChannelMessage(IRCChannel channel, IRCUser sender, String message) {
                System.out.println(sender.username() + " >> " + message + " ON " + channel.name());
            }
        });

        kThread.sleep(100000);
    }
}
