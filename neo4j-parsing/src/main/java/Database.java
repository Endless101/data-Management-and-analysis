import nodes.Query;
import org.neo4j.driver.*;


public class Database {

    Driver driver;

    Database() {
      driver = GraphDatabase.driver("bolt://localhost:11005",AuthTokens.basic("neo4j", "password"));
    }


   public void queryDatabase(Query query) {
    /* try (Session session = driver.session()) {
            session.run(query.query);
        }
*/
   }
}
