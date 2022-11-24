package nodes;

public class Relation {
    AbstractNode from;
    AbstractNode to;

    String relationType;

    public Relation(AbstractNode from, AbstractNode to, String relationType) {
        this.from = from;
        this.to = to;
        this.relationType = relationType;
    }
}
