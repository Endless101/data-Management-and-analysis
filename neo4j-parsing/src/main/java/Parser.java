import nodes.*;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.*;

public class Parser {

    static List<AbstractNode> relationsToProcess = new ArrayList<>();

    public static void parseConferenceOrJournal(String key, Map<String,String> contents) {
            String name = contents.get("booktitle");
            Map<String, String> newContents = new HashMap<>();
            newContents.put("key",key);
            AbstractNode node;
        if (key.startsWith("key='conf")) {
            newContents.put("conference", name);
            node = new ConferenceNode(newContents);
            Writer.writeHeader(node,node.getType()+".csv");
            Writer.writeHeader(node, node.getType()+".csv");
        } else if (key.startsWith("key=journal")) {
            newContents.put("journal",name);
            node = new JournalNode(newContents);
            Writer.writeHeader(node,node.getType()+".csv");
            Writer.writeHeader(node, node.getType()+".csv");
        }


    }

    public static void parseElements(XMLEventReader reader, String type, XMLEvent currentEvent, Database db) throws XMLStreamException {
        try {
            DatabaseEntities entityType = DatabaseEntities.valueOf(type);
            AbstractNode node;
            if (entityType == DatabaseEntities.proceedings || entityType == DatabaseEntities.article) {
                if(entityType == DatabaseEntities.article) {
                    Map<String,String> contents = parse(reader, currentEvent,currentEvent);
                    String key = currentEvent.asStartElement().getAttributeByName(new QName("key")).toString();
                    node = new ArticleNode(contents);
                    parseConferenceOrJournal(key,contents);

                } else {
                    Map<String,String> contents = parse(reader, currentEvent,currentEvent);
                    String key = currentEvent.asStartElement().getAttributeByName(new QName("key")).toString();
                    node = new ProceedingsNode(contents);
                    parseConferenceOrJournal(key,contents);
                }
                for(AbstractNode n : relationsToProcess) {
                   node.addRelation(new Relation(node,n,n.getType()));
                }
                relationsToProcess.clear();
                    Writer.writeHeader(node, node.getType()+".csv");
                    Writer.writeContent(node,node.getType()+".csv");


            }
        } catch (Exception ignored) {
    }
       }

    public static List<String> parseSingleElement(XMLEventReader reader, XMLEvent currentEvent,XMLEvent startEvent) throws XMLStreamException {
        List<String> typeAndContent = new ArrayList<>();
        if (currentEvent.isStartElement()) {
            try {
                DatabaseEntities elementType = DatabaseEntities.valueOf(currentEvent.asStartElement().getName().getLocalPart());
                currentEvent = reader.nextEvent();
                if (currentEvent.isCharacters() && !currentEvent.asCharacters().isIgnorableWhiteSpace()) {
                    String elementContent = currentEvent.asCharacters().getData();
                    if (elementType == DatabaseEntities.editor || elementType == DatabaseEntities.author) {
                        PersonNode pnode = parsePerson(elementType,elementContent, startEvent);
                        Writer.writeHeader(pnode, pnode.getType()+".csv");
                        Writer.writeContent(pnode, pnode.getType()+".csv");
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

    private static PersonNode parsePerson(DatabaseEntities personType, String name,XMLEvent startEvent) {
        String key = startEvent.asStartElement().getAttributeByName(new QName("key")).toString().replaceFirst("key=","");
        Map<String,String> personContents = new HashMap<>();
        personContents.put("key", key);
        personContents.put(personType.toString(),name);
        return new PersonNode(personType.toString(),personContents);
    }

    public static Map<String,String> parse(XMLEventReader reader, XMLEvent currentEvent, XMLEvent startElement) throws XMLStreamException {
        String key = startElement.asStartElement().getAttributeByName(new QName("key")).toString().replaceFirst("key=","");
        Map<String,String> contents = new HashMap<>();
        contents.put("key",key);
        if(startElement.asStartElement().getName().getLocalPart() == DatabaseEntities.proceedings.toString())
        {
            contents = ProceedingsNode.proceedingsMap();
            contents.put("key",key);
        } else {
            contents = ArticleNode.articleMap();
            contents.put("key",key);
        }
        while(!(currentEvent.isEndElement() &&
                        (currentEvent.asEndElement().getName().getLocalPart().equals(DatabaseEntities.proceedings.toString()) ||
                        currentEvent.asEndElement().getName().getLocalPart().equals(DatabaseEntities.article.toString())))) {
            if(currentEvent.isStartElement()) {
                List<String> parsedElement = parseSingleElement(reader, currentEvent,startElement);
                if(!parsedElement.isEmpty()) {
                    String type = parsedElement.get(0);
                    String content = parsedElement.get(1).replace(',',' ');
                    contents.put(type,content);
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




