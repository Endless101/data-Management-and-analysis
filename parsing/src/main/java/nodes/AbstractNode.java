package nodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AbstractNode implements DBNode {
    String type;
    Map<String,String> contents;

    List<Relation> relations = new ArrayList<>();

    public String getType() { return type; }

    public String[] getContent() {return contents.values().toArray(new String[0]);}


    @Override
    public String toString() {
        return "nodes.AbstractNode{" +
                "type='" + type + '\'' +
                ", contents=" + contents +
                '}';
    }

    AbstractNode(String type, Map<String,String> contents) {
        this.type = type;
        contents.replaceAll((k, v) -> clean(contents.get(k)));
        this.contents = contents;
    }
    private String clean(String s) {

        return "'" + s.replace("'"," ") + "'";
    }

    protected String createAttributeList() {
        List<String> attributes = new ArrayList<>();
        contents.forEach((k,v) -> attributes.add(k+": " + clean(v)));
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
               String toVar = "_"+ Math.abs(rand.nextInt());
               relationString = relationString + "MERGE " + r.to.createNode(toVar) + "-[:"+r.relationType+"]->" +"("+fromVar+")"+" ";
           }
       }
    return relationString;
    }
    @Override
    public Query createN4JInsertQuery() {
        String queryString = "CREATE "+ createNode(type) + " "+ createRelations(type);
        return new Query(queryString);
    }
}
