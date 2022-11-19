package nodes;

import java.util.List;
import java.util.Map;

public class JournalNode extends AbstractNode {
    public JournalNode(Map<String, List<String>> contents) {
        super("journal",contents);
    }
}
