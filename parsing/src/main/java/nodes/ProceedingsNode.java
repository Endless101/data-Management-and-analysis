package nodes;

import data_io.Writer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProceedingsNode extends AbstractNode{
    public static Map<String,String> proceedingsMap() {
    return Writer.CSVTypes.createMap(Writer.CSVTypes.proceedings);
    }
    public ProceedingsNode(Map<String,String> contents) {
        super("proceedings",contents);
    }

}
