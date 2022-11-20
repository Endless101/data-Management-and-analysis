import nodes.*;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.*;

public class Parser {

    static List<AbstractNode> relationsToProcess = new ArrayList<>();

    public static void parseConferenceOrJournal(String key, Map<String,List<String>> contents) {
            List<String> name = contents.get("booktitle");
            Map<String, List<String>> newContents = new HashMap<>();
            AbstractNode node;
        if (key.startsWith("key='conf")) {
            newContents.put("conference", name);
            node = new ConferenceNode(newContents);
            relationsToProcess.add(node);
         //   System.out.println(relationsToProcess.size());
        } else if (key.startsWith("key=journals")) {
            newContents.put("journal",name);
            node = new JournalNode(newContents);
            relationsToProcess.add(node);
        }


    }

    public static void parseElements(XMLEventReader reader, String type, XMLEvent currentEvent, Database db) throws XMLStreamException {
        try {
            DatabaseEntities entityType = DatabaseEntities.valueOf(type);
            AbstractNode node;
            if (entityType == DatabaseEntities.proceedings || entityType == DatabaseEntities.article) {
                if(entityType == DatabaseEntities.article) {
                    Map<String,List<String>> contents = parse(reader, currentEvent,currentEvent);
                    String key = currentEvent.asStartElement().getAttributeByName(new QName("key")).toString();
                    node = new ArticleNode(contents);
                    parseConferenceOrJournal(key,contents);

                } else {
                    Map<String,List<String>> contents = parse(reader, currentEvent,currentEvent);
                    String key = currentEvent.asStartElement().getAttributeByName(new QName("key")).toString();
                    node = new ProceedingsNode(contents);
                    parseConferenceOrJournal(key,contents);
                }
               //  System.out.println(relationsToProcess.size());
                for(AbstractNode n : relationsToProcess) {
                   node.addRelation(new Relation(node,n,n.getType()));
                }
                relationsToProcess.clear();
                db.queryDatabase(node.createN4JInsertQuery());


            }
        } catch (Exception ignored) {
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

    public static Map<String,List<String>> parse(XMLEventReader reader, XMLEvent currentEvent, XMLEvent startElement) throws XMLStreamException {
        Map<String,List<String>> contents= new HashMap<String,List<String>>();
        while(!(currentEvent.isEndElement() &&
                        (currentEvent.asEndElement().getName().getLocalPart().equals(DatabaseEntities.proceedings.toString()) ||
                        currentEvent.asEndElement().getName().getLocalPart().equals(DatabaseEntities.article.toString())))) {
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
        return contents;
    }


    public enum DatabaseEntities {
        article,
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




