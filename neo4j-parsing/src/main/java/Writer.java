import com.opencsv.*;
import nodes.AbstractNode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {

    public static void writeHeader(AbstractNode node, String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            try {
                String[] header = node.createHeader();
                FileWriter outputFile = new FileWriter(file);
                CSVWriter writer = new CSVWriter(outputFile, ',', CSVWriter.NO_QUOTE_CHARACTER);
                writer.writeNext(header);
                writer.flush();
                writer.close();

            } catch (Exception e) {
                System.err.println(e);
            }

        }
    }
    public static void writeContent(AbstractNode node, String filename) {
        File file = new File(filename);
       if(file.exists()){
           try {
               String[] content = node.createContent();
               FileWriter outputFile = new FileWriter(file,true);
               CSVWriter writer = new CSVWriter(outputFile,',',CSVWriter.NO_QUOTE_CHARACTER);
               writer.writeNext(content);
               writer.flush();
               writer.close();
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
       }
    }

    public static void main(String[] args) {

    }

}
