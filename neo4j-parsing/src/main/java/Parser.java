import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.HashMap;
import java.util.Map;

public class Parser {

    public static Map<String, String> parseElement(XMLEventReader reader, String type) throws XMLStreamException {
        Map<String, String> parsedElements = new HashMap<String, String>();
        //XMLEvent current = reader.peek();
        while (reader.hasNext()) {
            XMLEvent currentElement = reader.peek();
            if (currentElement.isEndElement() && type.equals(currentElement.asEndElement().getName().getLocalPart())) {
                break;
            } else if (currentElement.isStartElement()) {

                String elementType = currentElement.asStartElement().getName().getLocalPart();
                System.out.println(elementType);
                reader.nextEvent();
                currentElement = reader.nextEvent();
                if (currentElement.isCharacters()) {
                    parsedElements.put(elementType, currentElement.asCharacters().getData());
                }

            }
            reader.nextEvent();
        }
        return parsedElements;
    }

    public static void parseElements(XMLEventReader reader, String type, Database db) throws XMLStreamException {
        // XMLEvent currentEvent = reader.nextEvent();
        //if(currentEvent.isStartElement() && "phdthesis".equals( currentEvent.asStartElement().getName().getLocalPart())) O
        while (reader.hasNext()) {
            XMLEvent NewcurrentEvent = reader.peek();
            if (NewcurrentEvent.isEndElement() && type.equals(NewcurrentEvent.asEndElement().getName().getLocalPart())) {
                //System.out.println("end");
                break;
            } else if (NewcurrentEvent.isStartElement() && !type.equals(NewcurrentEvent.asStartElement().getName().getLocalPart())) {
                // System.out.println(NewcurrentEvent.asStartElement().getName().getLocalPart());
                Map<String, String> parsedElements = parseElement(reader, type);
                System.out.println(parsedElements);

                handleParsedElements(type, parsedElements, db);
            }
            reader.nextEvent();
        }
    }


    private static void handleParsedElements(String type, Map<String, String> parsedElements, Database db) {
        switch (type) {
            case "phdthesis": {
                PhDNode node = new PhDNode(type, parsedElements);
                Query query = node.insertIntoDB();
                db.queryDatabase(query);
                System.out.println("inserted");

            }
        }
    }


}


                /*   case "mastersthesis": {
                       List<String> parsedElements = parseElements(reader, "mastersthesis");
                       for (String s : parsedElements) {
                           System.out.println(s);
                       }
                   }
                   case "article": {
                       List<String> parsedElements = parseElements(reader, "article");
                       for (String s : parsedElements) {
                           System.out.println(s);
                           }
                       }
                case "proceedings": {
                    List<String> parsedElements = parseElements(reader, "proceedings");
                    for (String s : parsedElements) {
                        System.out.println(s);
                    }
                }
                 case "inproceedings": {
                     List<String> parsedElements = parseElements(reader, "inproceedings");
                     for (String s : parsedElements) {
                         System.out.println(s);
                     }
                 }*/


