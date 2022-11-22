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
    public void addRelation(Relation relation) { relations.add(relation); }

    public String[] getContent() {
       /* String[] cleanedContents = new String[contents.size()];
        List<String> contentValues = (List<String>) contents.values();
        for (int i = 0; i< contents.size(); i++) {
            cleanedContents[i] = contentValues.get()
        }*/
        return contents.values().toArray(new String[contents.size()]);
    }
    public String[] createHeader() { return contents.keySet().toArray(new String[contents.size()]);}


    @Override
    public String toString() {
        return "nodes.AbstractNode{" +
                "type='" + type + '\'' +
                ", contents=" + contents +
                '}';
    }

    AbstractNode(String type, Map<String,String> contents) {
        this.type = type;
        for(String key : contents.keySet()){
            contents.put(key,clean(contents.get(key)));
        }
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
       // System.out.println(contents);
       // System.out.println("Inserting into database");
        String queryString = "CREATE "+ createNode(type) + " "+ createRelations(type);
       // System.out.println(queryString);
        return new Query(queryString);
    }
}
