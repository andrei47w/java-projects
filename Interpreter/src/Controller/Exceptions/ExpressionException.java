package Controller.Exceptions;

public class ExpressionException extends Exception{
    public ExpressionException(String message){
        super("Expression exception: " + message);
    }
}
