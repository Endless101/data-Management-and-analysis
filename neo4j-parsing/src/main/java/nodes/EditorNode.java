package nodes;

import java.util.List;
import java.util.Map;

public class EditorNode extends PersonNode {
    public EditorNode(Map<String,String> contents) {
        super("editor", contents);
    }
}
