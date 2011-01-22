import java.io.StringReader;
import java.util.regex.Pattern;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Packet;


public class ChatListener implements PacketListener {
	public void processPacket(Packet p) {
		String from = p.getFrom();
		String xml = p.toXML();
		try {
			if(!from.equals("xdevbox@conference.xdevbox.net/XdevBoT")){
			  	//Xdevbot.muc2.sendMessage "hÃ¶r auf zu labern:"+from 
				
				XMLInputFactory factory = XMLInputFactory.newInstance(); 
				XMLStreamReader parser = factory.createXMLStreamReader(new StringReader(xml));
				boolean getbodytextnow = false;  // break
				while (parser.hasNext()) {
					switch (parser.getEventType()) {
					case XMLStreamConstants.START_ELEMENT:
						// System.out.println(parser.getLocalName());
						// XdevBoT.sendMessage(parser.getLocalName());
						if(parser.getLocalName().equals("body")){ getbodytextnow = true; }
						// System.out.println(getbodytextnow);
					break;
					
					case XMLStreamConstants.CHARACTERS:
						if(getbodytextnow){
							// XdevBoT.sendMessage(parser.getText());
							String[] commands = parser.getText().split(Pattern.quote(" "));
							for (ModInterfaceMucMsgRcvd m : XDevBoT.modMucMsgRcvd) {
								m.mucMsgRcvd(commands);
							}
							getbodytextnow = false;
						}
					break;
					}
					parser.next();
				}
			}
				//XdevBoT.muc2.sendMessage(xml);
			//} //catch (XMPPException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} catch(XMLStreamException e){
				e.printStackTrace();
			}
			}
		}

