//import jdk.internal.util.xml.XMLStreamException;

import java.io.FileNotFoundException;
import java.util.concurrent.Semaphore;

public class Main {
     final static Semaphore semaphore = new Semaphore(1);
    public static void main(String[] args) throws javax.xml.stream.XMLStreamException, FileNotFoundException, InterruptedException {
        Database db = new Database();
        try {
            semaphore.acquire();
            Reader.streamReader(db);
        } finally {
            semaphore.release();
        }
        System.out.println("finished");
    }

}
