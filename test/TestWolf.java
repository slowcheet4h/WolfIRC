package test;

import pisi.unitedmeows.yystal.utils.kThread;
import wolfirc.IRCChannel;
import wolfirc.IRCUser;
import wolfirc.WolfIRC;
import wolfirc.events.WOnChannelMessage;
import wolfirc.events.WOnMotdEnd;
import wolfirc.events.WOnRawData;

public class TestWolf {

    public static void main(String[] args) {

        WolfIRC wolfIRC = new WolfIRC("WolfIRCBot", "WolfIRC Bot");
        wolfIRC.connect("irc.freenode.net", 6667);
        wolfIRC.joinChannel("#wolfirc");

        wolfIRC.onChannelMessage.bind(new WOnChannelMessage() {
            @Override
            public void onChannelMessage(IRCChannel channel, IRCUser sender, String message) {
                System.out.println("channel message received");
            }
        });

        wolfIRC.useSendQueue();




        kThread.sleep(100000);
    }
}
