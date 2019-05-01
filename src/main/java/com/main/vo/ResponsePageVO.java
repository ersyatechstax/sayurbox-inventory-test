package com.main.vo;

import lombok.Data;

/**
 * created by ersya 30/03/2019
 */
@Data
public class ResponsePageVO extends ResponseVO {
    private int page;
    private long totalElements;
    private int totalPages;
}
