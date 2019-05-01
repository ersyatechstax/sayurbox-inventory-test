package com.main.controller;

import com.main.enums.StatusCode;
import com.main.exception.GeneralException;
import com.main.util.Constants;
import com.main.util.RestUtil;
import com.main.vo.ResponsePageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * created by ersya 30/03/2019
 */
@Slf4j
@Component
public abstract class AbstractPageRequestHandler {

    /**
     * page service injector
     * @return page object from service
     */
    public abstract Map<String,Object> processRequest();

    /**
     * get result page
     * @return @ResponseEntity
     */
    public ResponseEntity<ResponsePageVO> getResult(){
        ResponsePageVO responsePageVO = new ResponsePageVO();
        try {
            Map<String, Object> map = processRequest();
            if(map.get(Constants.PageParameter.TOTAL_ELEMENTS).equals(0)){
                responsePageVO.setTotalElements(0);
                responsePageVO.setPage(0);
                responsePageVO.setTotalPages(0);
                responsePageVO.setMessage(StatusCode.OK.name());
                responsePageVO.setResult(map.get(Constants.PageParameter.LIST_DATA));
            }
            else{
                responsePageVO.setTotalElements((Long) map.get(Constants.PageParameter.TOTAL_ELEMENTS));
                responsePageVO.setPage((Integer) map.get(Constants.PageParameter.PAGE));
                responsePageVO.setTotalPages((Integer) map.get(Constants.PageParameter.TOTAL_PAGES));
                responsePageVO.setMessage(StatusCode.OK.name());
                responsePageVO.setResult(map.get(Constants.PageParameter.LIST_DATA));
            }
        }catch (GeneralException e){
            responsePageVO.setMessage(e.getCode().name());
            responsePageVO.setResult(e.getMessage());
            log.error("ERROR: "+e.getMessage());
        }

        return RestUtil.getJsonResponse(responsePageVO);
    }
}
