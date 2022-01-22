# WolfIRC
WolfIRC for java is a java irc client library for making bots and clients<br>this project uses yystal lib (https://github.com/united-meows/yystal)


<h2>Documentation</h2>

```java
  WolfIRC client = new WolfIRC("WolfIRCBot", "WolfIRC Bot"); /* nick, description */
  client.connect("irc.freenode.net", 6667); /* irc server info */
  client.joinChannel("#wolfirc"); /* you can join more than 1 channel at any time */
  wolfIrc.useSendQueue(); /* (optional) queues the actions with a delay for ratelimiting */
  
  client.onChannelMessage.bind(new WOnChannelMessage() {
    @Override
    public void onChannelMessage(IRCChannel channel, IRCUser sender, String message) {
        System.out.println("channel message received");
    }
  });

```
<h4>Events</h4>
<b>onChannelMessageEvent</b> (gets called when someone sents a message on channel)<br>
<b>onRawDataEvent</b> (tcp client raw data)<br>
<b>onMotdEndEvent</b> (gets called when server done sending the motd)<br>
<b>onUserQuitEvent</b> (gets called when someone leaves the channel)<br>
<b>onPingEvent</b> (gets called when server sends ping/pong)<br>
<b>onUserNickChangeEvent</b> (gets called when someone in same room as you changes their nick)<br>
<b>onChannelJoinEvent</b> (gets called when you join a room)<br>
<b>onUserJoinChannelEvent</b> (gets called when someone joines the channel that you in)<br>
<br>

```java
  // IRCChannel
  channel.users(); /* returns list of users that connected on the channel */
  channel.leave(client); /* leaves the channel */
  channel.sendMessage(client, message); /* you can use client.sendMessage too */
  
  //IRCUser
  user.isOp(); /* returns if the user is operator */
  user.userName(); /* returns user's username */
  
  // WolfIRC
  client.setMode(String) /* sets the mode (like +x) */
  client.leaveChannel(String) /* leaves the channel */
  client.sendPM(String user, String message) /* sends pm */
  client.register(String password, String email) /* tries to register the server (!! Deprecated use sendRaw instead) */
  client.close(); /* closes the client */
  client.changeUsername(String) /* change nick */
  
  client.joinedRooms /* map of channels you have joined */
```

