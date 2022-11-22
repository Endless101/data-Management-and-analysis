package nodes;

import java.util.HashMap;
import java.util.Map;

public class InproceedingsNode extends AbstractNode {
   public static Map<String, String> inproceedingsMap(){
       Map<String,String> contents = new HashMap<>();
       contents.put("pages","null");
       contents.put("year","null");
       contents.put("booktitle", "null");
       contents.put("title", "null");
       contents.put("key", "null");
    return contents;
    }
    public InproceedingsNode(Map<String,String> contents) {
        super("inproceeding", contents);

    }
}
