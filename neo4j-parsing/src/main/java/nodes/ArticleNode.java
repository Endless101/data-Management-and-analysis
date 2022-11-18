package nodes;

import java.util.List;
import java.util.Map;

public class ArticleNode extends AbstractNode  {

    public ArticleNode(Map<String, List<String>> contents) {
        super("article", contents);
    }


}
