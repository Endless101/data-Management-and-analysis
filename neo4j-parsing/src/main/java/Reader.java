import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Reader {


    public static void streamReader(Database db) throws FileNotFoundException, javax.xml.stream.XMLStreamException {
        System.setProperty("entityExpansionLimit", "20000000");
        FileInputStream inputStream = new FileInputStream("resources/dblp.xml");
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = inputFactory.createXMLEventReader(inputStream);
        while (reader.hasNext()) {
            XMLEvent currentEvent = reader.nextEvent();
            if (currentEvent.isStartElement()) {
                switch (currentEvent.asStartElement().getName().getLocalPart()) {
                    case "phdthesis": {
                        Parser.parseElements(reader,"phdthesis",db);
                        }
                    }
                }
            }
        }
   }


