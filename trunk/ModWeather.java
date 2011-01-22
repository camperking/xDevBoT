import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;


public class ModWeather implements ModInterfaceMucMsgRcvd {
	public void mucMsgRcvd(String[] commands) {
		if (commands[0].equals("!wetter")) {   // check if first word is a command
			 // System.out.println(commands.length);
			 if (commands.length == 1) {
				 XDevBoT.sendMessage("usage: !wetter yourcity"); 
			 } else {
				URL weather = null;
				try {
				weather = new URL("http://www.google.com/ig/api?weather="+commands[1]+"&hl=en");
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
		}
		
		
		XMLInputFactory factory = XMLInputFactory.newInstance(); 
		XMLStreamReader parser = null;
		try {
			parser = factory.createXMLStreamReader(weather.openStream());
		} catch (XMLStreamException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String region = "";
		String temperature = "";
		String condition = "";
		String humidity = "";
		String wind = "";
		try {
			boolean breakwhile = false;
			while (parser.hasNext()) {
				if (breakwhile) { break; }
				switch (parser.getEventType()) {
				case XMLStreamConstants.START_ELEMENT:
					//System.out.println(parser.getLocalName());
					//XdevBoT.sendMessage(parser.getLocalName());
					if(parser.getLocalName().equals("city")){ region = parser.getAttributeValue(0); }
					if(parser.getLocalName().equals("temp_c")){ temperature = parser.getAttributeValue(0); }
					if(parser.getLocalName().equals("condition")){ condition = parser.getAttributeValue(0); }
					if(parser.getLocalName().equals("humidity")){ humidity = parser.getAttributeValue(0); }
					if(parser.getLocalName().equals("wind_condition")){ wind = parser.getAttributeValue(0); }
					if(parser.getLocalName().equals("forecast_conditions")){ breakwhile = true; }
				break;
				}
				parser.next();
			}
			String message;
			message = "Wetterlage " + region + ": " + condition + ", " + temperature + "°C " + wind + " " + humidity;
			XDevBoT.sendMessage(message);
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			 }
		}
	}
}
