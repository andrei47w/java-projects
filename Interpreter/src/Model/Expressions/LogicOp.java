package Model.Expressions;

public enum LogicOp {
    AND("&&"),
    OR("||");

    public final String label;

    LogicOp(String label) {
        this.label = label;
    }
}
