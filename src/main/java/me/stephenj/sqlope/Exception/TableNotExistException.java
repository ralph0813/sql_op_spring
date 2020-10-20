package me.stephenj.sqlope.Exception;

public class TableNotExistException extends Exception{
    public TableNotExistException() {
    }

    public TableNotExistException(String message) {
        super(message);
    }
}
