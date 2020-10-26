package me.stephenj.sqlope.Exception;

public class UserExistException extends Exception{
    public UserExistException() {
    }

    public UserExistException(String message) {
        super(message);
    }
}
