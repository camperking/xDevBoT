import java.util.ArrayList;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.MultiUserChat;


public class XDevBoT {
	static String xmppserver = "xdevbox.net";
	static int xmppport = 5222;
	static String botname = "xDevBoT";
	static String password = "password";
	static String ressource = "xDevBoT";
	
	static MultiUserChat muc2;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Create the configuration for this new connection
		ConnectionConfiguration config = new ConnectionConfiguration(xmppserver, xmppport);
		config.setCompressionEnabled(true);
		config.setSASLAuthenticationEnabled(true);
		
		XMPPConnection connection = new XMPPConnection(config);
		// Connect to the server
		try {
			connection.connect();
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Log into the server
		try {
			connection.login(botname, password, ressource);
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Create a MultiUserChat using an XMPPConnection for a room
        muc2 = new MultiUserChat(connection, "xdevbox@conference.xdevbox.net");
        ModLoader.loadModMucMsgRcvd();
        // User2 joins the new room using a password and specifying
        // the amount of history to receive. In this example we are requesting the last 5 messages.
        DiscussionHistory history = new DiscussionHistory();
        history.setMaxStanzas(1);
        history.setSeconds(1);
        try {
			muc2.join("XdevBoT", null, history, SmackConfiguration.getPacketReplyTimeout());
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			muc2.sendMessage("geilomat");
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		muc2.addMessageListener(new ChatListener());
		while(true){try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
		//connection.disconnect();
	}
	
	static void sendMessage(String message) {
		try {
			muc2.sendMessage(message);
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static ArrayList<ModInterfaceMucMsgRcvd> modMucMsgRcvd = new ArrayList<ModInterfaceMucMsgRcvd>();
	
	static void addModMucMsgRcvd(ModInterfaceMucMsgRcvd m) {
		modMucMsgRcvd.add(m);
	}
}
