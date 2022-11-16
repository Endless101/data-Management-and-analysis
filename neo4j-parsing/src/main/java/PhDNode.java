import sun.awt.X11.XSystemTrayPeer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PhDNode extends AbstractNode implements DBNode {
    PhDNode(String type, Map<String,String> contents) {
        super(type, contents);
    }

    @Override
    public String toString() {
        return super.toString();
    }
    private String clean(String s) {

        return "'" + s.replace("'"," ") + "'";
     //   return "'M'Lady'";
    }
    @Override
    public Query insertIntoDB   () {
        System.out.println("Inserting into database");
        List<String> attributes = new ArrayList<String>();
        contents.forEach((k,v) -> {
            attributes.add(k+": " + clean(v));
        });
       String contentString = attributes.toString();
        int contentsStringLength = contentString.length();
       String finalContentString = "{"+contentString.substring(1, (contentsStringLength-1))+"}";
        String queryString = "CREATE (e:thesis " +finalContentString+ ") RETURN 'done'";
        System.out.println(queryString);
        return new Query(queryString);

    }
}
