package nodes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleNode extends AbstractNode  {

    public static Map<String,String> articleMap() {
       Map<String,String> contents = new HashMap<>();
       contents.put("volume","null");
       contents.put("number","null");
       contents.put("pages","null");
       contents.put("journal", "null");
       contents.put("year","null");
       contents.put("title", "null");
       contents.put("key", "null");

       /*contents.put("publisher","null");
       contents.put("booktitle","null");
       contents.put("volume",null);*/
    return contents;
    }
    public ArticleNode(Map<String,String> contents) {
        super("article", contents);
    }


}
