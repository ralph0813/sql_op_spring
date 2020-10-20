package me.stephenj.sqlope.Exception;

public class TableExistException extends Exception{
    public TableExistException() {
    }

    public TableExistException(String message) {
        super(message);
    }
}
