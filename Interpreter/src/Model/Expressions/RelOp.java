package Model.Expressions;

public enum RelOp {
    LOWER("<"),
    LOWERE("<="),
    EQUAL("=="),
    NOTEQUAL("!="),
    HIGHER(">"),
    HIGHERE(">=");

    public final String label;

    RelOp(String label) {
        this.label = label;
    }
}