import jdk.internal.util.xml.XMLStreamException;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws XMLStreamException, javax.xml.stream.XMLStreamException, FileNotFoundException {
        Database db = new Database();
        Reader.streamReader(db);
    }

}
