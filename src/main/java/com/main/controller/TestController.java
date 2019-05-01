package com.main.controller;

import com.main.services.AuthService;
import com.main.services.TestService;
import com.main.vo.ResponsePageVO;
import com.main.vo.ResponseVO;
import com.main.vo.TestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * created by ersya 30/03/2019
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    TestService testService;

    @Autowired
    AuthService authService;


    @GetMapping("/auth")
    public ResponseEntity<ResponseVO> authenticateUser(){
        AbstractRequestHandler handler = new AbstractRequestHandler() {
            @Override
            public Object processRequest() {
                return authService.getCurrentUsername();
            }
        };
        return handler.getResult();
    }

    @GetMapping("/find-all")
    public ResponseEntity<ResponsePageVO> findAll(
            @RequestParam(value = "page",defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "25") Integer limit,
            @RequestParam(value = "sort_by",defaultValue = "id") String sortBy,
            @RequestParam(value = "sort_direction",defaultValue = "asc") String sortDirection
    ){
        AbstractPageRequestHandler handler = new AbstractPageRequestHandler() {
            @Override
            public Map<String, Object> processRequest() {
                return testService.findAll(page,limit,sortBy,sortDirection);
            }
        };
        return handler.getResult();
    }

    @GetMapping("/detail")
    public ResponseEntity<ResponseVO> getDetail(
            @RequestParam(value = "id") String id
    ){
        AbstractRequestHandler handler = new AbstractRequestHandler() {
            @Override
            public Object processRequest() {
                return testService.getDetail(id);
            }
        };
        return handler.getResult();
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseVO> create(
            @Valid @RequestBody TestVO testVO, Errors errors
    ){
        AbstractRequestHandler handler = new AbstractRequestHandler() {
            @Override
            public Object processRequest() {
                return testService.create(testVO);
            }
        };
        return handler.getResultWithValidation(errors);
    }
//
//    public ResponseEntity<ResponsePageVO> getResult(Page page){
//        return AbstractRequestHandler.getResult(page);
//    }
}
