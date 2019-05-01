package com.main.controller;

import com.main.enums.StatusCode;
import com.main.exception.GeneralException;
import com.main.util.RestUtil;
import com.main.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import java.util.stream.Collectors;

/**
 * created by ersya 30/03/2019
 */
@Slf4j
@Component
public abstract class AbstractRequestHandler {

    /***
     * service injector
     * @return object from a service
     */
    public abstract Object processRequest();

    /**
     * get result with payload validation (only for post method)
     * @param errors errors from javax validation
     * @return @ResponseEntity
     */
    public ResponseEntity<ResponseVO> getResultWithValidation(Errors errors){
        if(errors.hasErrors()){
            ResponseVO responseVO = new ResponseVO();
            String errorMessages = errors.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
            responseVO.setMessage(HttpStatus.BAD_REQUEST.name());
            responseVO.setResult(errorMessages);
            return RestUtil.getJsonResponse(responseVO,HttpStatus.BAD_REQUEST);
        }
        else{
            return getResult();
        }
    }

    /**
     * get result
     * @return @ResponseEntity
     */
    public ResponseEntity<ResponseVO> getResult(){
        ResponseVO responseVO = new ResponseVO();
        try {
            Object o =  processRequest();
            if(o != null){
                responseVO.setMessage(StatusCode.OK.toString());
                responseVO.setResult(o);
            }
            else{
                responseVO.setMessage(StatusCode.OK.toString());
                responseVO.setResult(null);
            }
        }catch (GeneralException e){
            responseVO.setMessage(e.getCode().name());
            responseVO.setResult(e.getMessage());
            log.error("ERROR: "+ e);
        }
        return RestUtil.getJsonResponse(responseVO);
    }

}
