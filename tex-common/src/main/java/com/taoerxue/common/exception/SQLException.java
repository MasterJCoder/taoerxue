package com.taoerxue.common.exception;

/**
 * Created by lizhihui on 2017-03-16 15:02.
 */
public class SQLException extends RuntimeException implements CustomException{


    public SQLException(String message) {
        super(message);
    }
}
