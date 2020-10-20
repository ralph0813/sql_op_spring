package me.stephenj.sqlope.Exception;

/**
 * @ClassName DataNotComplete.java
 * @Description
 * @author 张润天
 * @Time 2020/10/20 11:23
 * @Field :
 */
public class DataNotCompleteException extends Exception{
    public DataNotCompleteException() {
    }

    public DataNotCompleteException(String message) {
        super(message);
    }
}
