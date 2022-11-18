import nodes.*;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.*;

public class Parser {

    static List<AbstractNode> relationsToProcess = new ArrayList<>();

    public static void parseElements(XMLEventReader reader, String type, XMLEvent currentEvent, Database db) throws XMLStreamException {
       switch (type) {
           case "proceedings": {
              AbstractNode node = new ProceedingsNode(parseProceeding(reader,currentEvent));
              node.relations = relationsToProcess;
              System.out.println(node.createN4JInsertQuery("EDITOR").query);
           }
       }
    }



    public static List<String> parseSingleElement(XMLEventReader reader, XMLEvent currentEvent) throws XMLStreamException {
        List<String> typeAndContent = new ArrayList<String>();
        if (currentEvent.isStartElement()) {
            try {
                DatabaseEntities elementType = DatabaseEntities.valueOf(currentEvent.asStartElement().getName().getLocalPart());
                currentEvent = reader.nextEvent();
                if (currentEvent.isCharacters() && !currentEvent.asCharacters().isIgnorableWhiteSpace()) {
                    String elementContent = currentEvent.asCharacters().getData();
                    if (elementType == DatabaseEntities.editor || elementType == DatabaseEntities.author) {
                        relationsToProcess.add(parsePerson(elementType, elementContent));
                    } else {
                        typeAndContent.add(elementType.toString());
                        typeAndContent.add(elementContent);
                    }
                }
            } catch(Exception e) {
                reader.nextEvent();
            }
        }
        return typeAndContent;
    }

    private static PersonNode parsePerson(DatabaseEntities personType, String name) {
        Map<String,List<String>> personContents = new HashMap<>();
        List<String> nameInList = new ArrayList<>();
        nameInList.add(name);
        personContents.put(personType.toString(),nameInList);
        return new PersonNode(personType.toString(),personContents);
    }

    public static Map<String,List<String>> parseProceeding(XMLEventReader reader, XMLEvent currentEvent) throws XMLStreamException {
        Map<String,List<String>> contents= new HashMap<String,List<String>>();
        while(!(currentEvent.isEndElement() && currentEvent.asEndElement().getName().getLocalPart().equals(DatabaseEntities.proceedings.toString()))) {
            if(currentEvent.isStartElement()) {
                List<String> parsedElement = parseSingleElement(reader, currentEvent);
                if(!parsedElement.isEmpty()) {
                    String type = parsedElement.get(0);
                    String content = parsedElement.get(1);
                    if(!contents.containsKey(type)) {
                        List<String> bucket = new ArrayList<String>();
                        bucket.add(content);
                        contents.put(type, bucket);
                    } else {
                       List<String> updatedBucket = contents.get(type);
                       updatedBucket.add(content);
                       contents.put(type,updatedBucket);
                    }
                }
            }
         currentEvent = reader.nextEvent();
        }
      // System.out.println(contents);
        return contents;
    }


    public enum DatabaseEntities {
        proceedings,
        editor,
        title,
        booktitle,
        publisher,
        volume,
        year,
        author,
        journal,
        number,
        pages;
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


