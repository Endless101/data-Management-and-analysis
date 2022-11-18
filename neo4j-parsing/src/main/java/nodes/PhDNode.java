package nodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PhDNode extends AbstractNode {
    public PhDNode(Map<String,List<String>> contents) {
        super("phdthesis", contents);
    }

}
