package me.stephenj.sqlope.Exception;

/**
 * @ClassName DatabaseNotExistException.java
 * @Description
 * @author 张润天
 * @Time 2020/10/20 21:31
 * @Field :
 */
public class DatabaseNotExistException extends Exception{
    public DatabaseNotExistException() {
    }

    public DatabaseNotExistException(String message) {
        super(message);
    }
}
