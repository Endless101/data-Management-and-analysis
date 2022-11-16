import java.util.List;
import java.util.Map;

public class AbstractNode {
    String type;
    Map<String,String> contents;

    @Override
    public String toString() {
        return "AbstractNode{" +
                "type='" + type + '\'' +
                ", contents=" + contents +
                '}';
    }

    AbstractNode(String type, Map<String,String> contents) {
        this.type = type;
        this.contents = contents;
    }
}
