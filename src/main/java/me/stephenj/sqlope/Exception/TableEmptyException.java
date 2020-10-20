package me.stephenj.sqlope.Exception;

/**
 * @ClassName TableEmptyException.java
 * @Description
 * @author 张润天
 * @Time 2020/10/20 18:21
 * @Field :
 */
public class TableEmptyException extends Exception{
    public TableEmptyException() {
    }

    public TableEmptyException(String message) {
        super(message);
    }
}
