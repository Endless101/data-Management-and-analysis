package nodes;

import java.util.List;
import java.util.Map;

public class AuthorNode extends PersonNode {
    public AuthorNode(Map<String, List<String>> contents) {
        super("author", contents);
    }
}
