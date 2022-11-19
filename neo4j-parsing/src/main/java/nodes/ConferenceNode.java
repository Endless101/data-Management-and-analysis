package nodes;

import java.util.List;
import java.util.Map;

public class ConferenceNode extends AbstractNode {

    public ConferenceNode(Map<String, List<String>> contents) {
        super("conference", contents);
    }
}
