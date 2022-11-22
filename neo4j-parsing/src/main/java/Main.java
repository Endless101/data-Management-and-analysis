//import jdk.internal.util.xml.XMLStreamException;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws javax.xml.stream.XMLStreamException, FileNotFoundException, InterruptedException {
        //Database db = new Database();
        Writer.CSVTypes.createFiles();;
        Reader.streamReader();
        System.out.println("finished");
    }

}
