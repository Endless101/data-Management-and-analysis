package nodes;

import java.util.List;
import java.util.Map;

public class PersonNode extends AbstractNode {
    public PersonNode(String type, Map<String, List<String>> contents) {
        super(type, contents);

    }
    public String getName() {
        return contents.get(type).get(0);
    }
}
