package com.main.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * created by ersya 30/03/2019
 */
public class RestUtil {
    private static final String CONTENT_TYPE = "Content-Type";

    /**
     * get JSON response from object
     * @param src source class
     * @param <T> source class
     * @return @ResponseEntity
     */
    public static <T> ResponseEntity<T> getJsonResponse(T src){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return new ResponseEntity<T>(src,httpHeaders, HttpStatus.OK);
    }

    /**
     * get JSON response from object with http status
     * @param src source class
     * @param httpStatus http status
     * @param <T> source class
     * @return @ResponseEntity
     */
    public static <T> ResponseEntity<T> getJsonResponse(T src, HttpStatus httpStatus){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return new ResponseEntity<T>(src,httpHeaders,httpStatus);
    }
}
