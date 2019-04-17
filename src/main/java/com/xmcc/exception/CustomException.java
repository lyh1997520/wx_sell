package com.xmcc.exception;

import com.xmcc.common.ResultEnums;

public class CustomException extends RuntimeException {
    private int code;

    public CustomException() {
        super();
    }

    public CustomException(String message) {
       this(ResultEnums.FAIL.getCode(),message);
    }

    public CustomException(int code,String message) {
       this.code=code;
    }
}
