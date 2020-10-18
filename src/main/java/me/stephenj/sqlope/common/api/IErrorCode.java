package me.stephenj.sqlope.common.api;

/**
 * 封装API的错误码
 * Created by stephen on 2020/10/3.
 */
public interface IErrorCode {
    long getCode();

    String getMessage();
}
