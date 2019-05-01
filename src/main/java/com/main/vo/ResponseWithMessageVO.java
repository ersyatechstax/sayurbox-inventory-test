package com.main.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ResponseWithMessageVO extends BaseVO {
    private String responseMessage;

    public ResponseWithMessageVO(String id, Integer version, String responseMessage){
        this.id = id;
        this.version = version;
        this.responseMessage = responseMessage;
    }
}
