import com.opencsv.*;
import nodes.AbstractNode;

import java.io.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Writer {


    public enum CSVTypes {
        article(new String[] {"volume","number","pages","journal", "year","publisher","title","key"}),
        inproceeding(new String[] {"pages", "year","booktitle","title","key"}),
        conference(new String[] {"conference", "key"}),
        editor(new String[] {"editor","key"}),
        proceedings(new String[] {"volume","year","publisher","booktitle","title","key"}),
        author(new String[] {"author", "key"}),
        journal(new String[] {"journal", "key"});

        final String[] header;
        CSVTypes(String[] header) {
            this.header = header;
        }

        static void createFiles() {
            for (CSVTypes type : CSVTypes.values()) {
                writeHeader(type.header, type.toString()+".csv");
            }
        }

    }





    public static void writeHeader(String[] header,String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            try {
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
               String[] content = node.getContent();
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

    public static void splitCSV(String filename) throws IOException, InterruptedException {
        File inputfile = new File(filename + ".csv");
        FileReader reader = new FileReader(inputfile);
        CSVReader csvReader = new CSVReader(reader,',');
        int lineCount = 1;
        int fileCount = 1;
        File newFile = new File(filename + "-" + fileCount + ".csv");
        FileWriter writer = new FileWriter(newFile);
        CSVWriter csvWriter = new CSVWriter(writer);
        csvWriter.writeNext(CSVTypes.article.header);
        String[] line;
        while ((line = csvReader.readNext()) != null) {
            if (lineCount % 100000 == 0) {
                fileCount++;
                newFile = new File(filename + "-" + fileCount + ".csv");
                writer = new FileWriter(newFile);
                csvWriter = new CSVWriter(writer);
                csvWriter.writeNext(CSVTypes.article.header);
            } else {
               // System.out.println(Arrays.toString(toRead));
                csvWriter.writeNext(line);
                csvWriter.flush();
                lineCount++;
            }
        }
    }

        public static void main(String[] args) throws IOException, InterruptedException {
        splitCSV("article");

        }
    }


