package Model.Expressions;

public enum ArithOp {
    ADDITION("+"),
    SUBTRACTION("-"),
    MULTIPLICATION("*"),
    DIVISION("/");

    public final String label;

    ArithOp(String label) {
        this.label = label;
    }
}

