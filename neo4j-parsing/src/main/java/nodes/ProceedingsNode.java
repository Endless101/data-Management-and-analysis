package nodes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProceedingsNode extends AbstractNode{
    public static Map<String,String> proceedingsMap() {
       Map<String,String> contents = new HashMap<>();
       contents.put("volume","null");
       contents.put("year","null");
       contents.put("publisher","null");
       contents.put("booktitle","null");
       contents.put("title","null");
       contents.put("key", "null");
      // contents.put("volume",null);
    return contents;
    }
    public ProceedingsNode(Map<String,String> contents) {
        super("proceedings",contents);
    }

}
