package com.main.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * created by ersya 30/03/2019
 */
@Data
public class ResponseVO {

    private String message;

    private Object result;

    public ResponseVO(){}


    public ResponseVO(String message,Object result){
        this.message = message;
        this.result = result;
    }
}
