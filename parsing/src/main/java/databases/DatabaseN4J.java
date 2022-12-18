package databases;

import nodes.Query;
import org.neo4j.driver.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class DatabaseN4J {

       static Session session;
        class HandleTransaction extends Thread {
            Session session;
            List<Query> queries;

            public HandleTransaction(List<Query> queries) {
                this.queries = queries;
                session = driver.session();
            }

            @Override
            public void run() {
                for (int i = 0; i <5; i++){
                    try (Transaction tx = session.beginTransaction()) {
                        for (Query q : queries) {
                            tx.run(q.query);
                        }
                        //System.out.println("commited");
                    } catch (Throwable e) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        }
        Driver driver;

        static String queriesPath = "resources/insert_data.cypher";
       volatile List<Query> queries = new ArrayList<>();

        public DatabaseN4J() {
            driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "password"));
            session = driver.session();
        }
      synchronized  public void queryDatabase(Query query) {
            if(queries.size() == 1000) {
                for(Query q: queries) {
                    session.run(q.query);
                }
                queries.clear();
            } else {
               // System.out.println("adding query");
                queries.add(query);
            }
            //session.run(query.query);
        }
        public static String[] readQueries(String queriesPath) throws IOException {
            File queriesFile = new File(queriesPath);
            byte[] fileBytes = Files.readAllBytes(queriesFile.toPath());
            return (new String(fileBytes)).split(";");
    }
    public void executeQueries() {
            try {
               String[] queries = readQueries(queriesPath);
               for(String q: queries) {
                   session.run(q);
                   System.out.println("executed: "+ q);
               }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
    }
    public static void main(String[] args) throws IOException {
            DatabaseN4J db = new DatabaseN4J();
            db.executeQueries();
            db.driver.close();
        }
    }
