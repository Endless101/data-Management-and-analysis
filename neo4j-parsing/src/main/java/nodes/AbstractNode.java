package nodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AbstractNode implements DBNode {
    String type;
    Map<String, List<String>> contents;

    List<Relation> relations = new ArrayList<>();

    public String getType() {
        return type;
    }
    public void addRelation(Relation relation) {
        relations.add(relation);
    }

    @Override
    public String toString() {
        return "nodes.AbstractNode{" +
                "type='" + type + '\'' +
                ", contents=" + contents +
                '}';
    }

    AbstractNode(String type, Map<String, List<String>> contents) {
        this.type = type;
        this.contents = contents;
    }
    private String clean(String s) {

        return "'" + s.replace("'"," ") + "'";
    }

    protected String createAttributeList() {
        List<String> attributes = new ArrayList<String>();
        contents.forEach((k,v) -> {
            if(v.size()==1) {
            attributes.add(k+": " + clean(v.get(0)));
        }});
       String contentString = attributes.toString();
        int contentsStringLength = contentString.length();
        return "{"+contentString.substring(1, (contentsStringLength-1))+"}";
    }
    private String createNode(String variable) {
         String finalContentString = createAttributeList();
        return "("+variable+":"+ type+" " + finalContentString+ ")";
    }


    public String createRelations(String fromVar) {
        String relationString = "";
        Random rand = new Random();
       if(!relations.isEmpty()){
           for(Relation r: relations) {
               String toVar = "_"+Integer.toString(Math.abs(rand.nextInt()));
               relationString = relationString + "MERGE " + r.to.createNode(toVar) + "-[:"+r.relationType+"]->" +"("+fromVar+")"+" ";
           }
       }
    return relationString;
    }
    @Override
    public Query createN4JInsertQuery() {
       // System.out.println("Inserting into database");
        String queryString = "CREATE "+ createNode(type) + " "+ createRelations(type);
       // System.out.println(queryString);
        return new Query(queryString);
    }
}
