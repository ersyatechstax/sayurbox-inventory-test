package com.main.exception;

import com.main.enums.StatusCode;
import lombok.Data;

/**
 * created by ersya 30/03/2019
 */
@Data
public class GeneralException extends RuntimeException{

    public StatusCode code = StatusCode.ERROR;

    public GeneralException(String message){
        super(message);
    }

    public GeneralException(String message, StatusCode statusCode){
        super(message);
        this.code = statusCode;
    }
}
