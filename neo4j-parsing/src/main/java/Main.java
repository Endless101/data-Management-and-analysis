//import jdk.internal.util.xml.XMLStreamException;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws javax.xml.stream.XMLStreamException, FileNotFoundException, InterruptedException {
        Database db = new Database();
        Reader.streamReader(db);
        System.out.println("finished");
    }

}
