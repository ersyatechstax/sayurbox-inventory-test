package com.main.controller;

import com.main.services.AuthService;
import com.main.vo.LoginVO;
import com.main.vo.RegisterVO;
import com.main.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * created by ersya 30/03/2019
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseVO> doLogin(@RequestBody LoginVO loginVO, Errors errors){
        AbstractRequestHandler handler = new AbstractRequestHandler() {
            @Override
            public Object processRequest() {
                return authService.doLogin(loginVO);
            }
        };
        return handler.getResultWithValidation(errors);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseVO> registerUser(@Valid @RequestBody RegisterVO registerVO, Errors errors){
        AbstractRequestHandler handler = new AbstractRequestHandler() {
            @Override
            public Object processRequest() {
                return authService.register(registerVO);
            }
        };
        return handler.getResultWithValidation(errors);
    }
}
