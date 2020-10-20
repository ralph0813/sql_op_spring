package me.stephenj.sqlope.Exception;

/**
 * @ClassName DataExistException.java
 * @Description
 * @author 张润天
 * @Time 2020/10/20 19:38
 * @Field :
 */
public class DataExistException extends Exception{
    public DataExistException() {
    }

    public DataExistException(String message) {
        super(message);
    }
}
