package com.main.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * created by ersya 30/03/2019
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseVO {
    protected String id;
    protected Integer version;

    public BaseVO(){

    }

    public BaseVO(String id, Integer version){
        this.id = id;
        this.version = version;
    }
}