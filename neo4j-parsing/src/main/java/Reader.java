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
        int i = 0;
        while (reader.hasNext()) {
            if(i % 100000000 == 0) {
                System.out.println(i);
            }
            XMLEvent currentEvent = reader.peek();
            if (currentEvent.isStartElement()) {
               Parser.parseElements(reader,currentEvent.asStartElement().getName().getLocalPart(),currentEvent,db);
            }
            i++;
            reader.nextEvent();

            }
        }
   }


