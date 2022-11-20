import nodes.Query;
import org.neo4j.driver.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
public class Database {

        class HandleTransaction extends Thread{
            Session session;
            List<Query> queries;

            public HandleTransaction(List<Query> queries) {
                this.queries = queries;
                session = driver.session();
            }

            @Override
            public void run() {
                Query[] queriesArray = queries.toArray(new Query[queries.size()]);

                try {
                    session.writeTransaction(tx -> {
                        for (Query q : queriesArray) {
                            tx.run(q.query);
                        }
                        tx.commit();
                        //System.out.println("commited");
                        return "Hello" ;
                    });
                } catch (Exception e) {
                    System.out.println(e);
                }
                System.out.println("Finished" + this.toString());
            }
        }
        Driver driver;


        List<Query> queries = new ArrayList<>();

        public Database() {
            driver = GraphDatabase.driver("bolt://localhost:11003", AuthTokens.basic("neo4j", "password"));

        }

        public void queryDatabase(Query query) {
            if(queries.size() == 1000) {
               // System.out.println(queries);
                HandleTransaction transactionThread = new HandleTransaction(queries);
                queries.clear();
                transactionThread.start();

            } else {
               // System.out.println("adding query");
                queries.add(query);
            }
        }
    }
