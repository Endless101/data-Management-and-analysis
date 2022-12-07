import nodes.Query;
import org.neo4j.driver.*;

import java.util.ArrayList;
import java.util.List;
public class DatabaseN4J {

        Session session;
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
    }
