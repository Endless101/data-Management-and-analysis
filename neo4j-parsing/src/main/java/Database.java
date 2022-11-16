import org.neo4j.driver.*;


public class Database {

    Driver driver;

    Database() {
      driver = GraphDatabase.driver("bolt://localhost:7687",AuthTokens.basic("neo4j", "password"));
    }


   public void queryDatabase(Query query) {
       try (Session session = driver.session()) {
            String greeting = session.writeTransaction(tx -> {
                Result result = tx.run(
                        query.query,
                        org.neo4j.driver.Values.parameters("message", "Hello"));
                return result.single().get(0).asString();
            });
            System.out.println(greeting);
        }
   }
}
