package me.stephenj.sqlope.Exception;

public class ParameterLackException extends Exception{
    public ParameterLackException() {
    }

    public ParameterLackException(String message) {
        super(message);
    }
}
