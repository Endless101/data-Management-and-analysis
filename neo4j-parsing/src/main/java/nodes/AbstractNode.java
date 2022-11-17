package nodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AbstractNode implements DBNode {
    String type;
    Map<String,String> contents;

    @Override
    public String toString() {
        return "nodes.AbstractNode{" +
                "type='" + type + '\'' +
                ", contents=" + contents +
                '}';
    }

    AbstractNode(String type, Map<String,String> contents) {
        this.type = type;
        this.contents = contents;
    }
    private String clean(String s) {

        return "'" + s.replace("'"," ") + "'";
    }

    protected String createAttributeList() {
        List<String> attributes = new ArrayList<String>();
        contents.forEach((k,v) -> {
            attributes.add(k+": " + clean(v));
        });
       String contentString = attributes.toString();
        int contentsStringLength = contentString.length();
        return "{"+contentString.substring(1, (contentsStringLength-1))+"}";
    }

    @Override
    public Query createN4JInsertQuery() {
        System.out.println("Inserting into database");
        String finalContentString = createAttributeList();
        String queryString = "CREATE (e:"+ type+" " + finalContentString+ ") RETURN 'done'";
        System.out.println(queryString);
        return new Query(queryString);
    }
}
