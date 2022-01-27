package Controller.Exceptions;

public class HeapException extends Exception{
    public HeapException(String message){super("Statement exception: " + message);}
}
