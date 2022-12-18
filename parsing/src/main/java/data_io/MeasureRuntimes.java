package data_io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MeasureRuntimes {

    public static void main(String[] args) {

        if(args.length < 1){
            System.out.println("Please provide '# repetitions to perform' as commandline argument.");
            return;
        }
        int n_repetitions = Integer.parseInt(args[0]);
        System.out.println("Repeating each measurement "+n_repetitions+" times.");
        if(args.length > 1){
            System.out.println("You are using too many arguments.");
            return;
        }

        File res_file = new File("MDB.csv");

        System.out.println("Starting benchmarks");

        // measure runtimes
        List<Long> rts = benchmark(n_repetitions);

        // write to file
        write2file(res_file, rts);
        System.out.println("v");
    }

    // writes runtimes for a given strategy to file
    static private void write2file(File f, List<Long> runtimes){
        PrintWriter csv_writer;
        try {
            csv_writer = new PrintWriter(new FileOutputStream(f,true));
            String line = "";
            for(Long rt : runtimes){
                line += ","+rt;
            }
            csv_writer.println(line);
            csv_writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static public List<Long> benchmark(int nrep){
        List<Long> runtimes = new ArrayList<Long>(nrep);
        for(int i = 0; i < nrep; i++){

            // Timing the E1 query
            long beforeE1Query = System.nanoTime();
            // EXECUTE EI QUERY HERE
            long afterE1Query = System.nanoTime()-beforeE1Query;
            runtimes.add(afterE1Query);

            // Timing the E2 query
            long beforeE2Query = System.nanoTime();
            // EXECUTE E2 QUERY HERE
            long afterE2Query = System.nanoTime()-beforeE2Query;
            runtimes.add(afterE2Query);

            // ADD MORE QUERIES

            // Total runtime of the ParallelAnalyser
            runtimes.add(afterE1Query + afterE2Query);

            System.out.print(i + " ");
            System.out.flush();
        }
        return runtimes;
    }
}