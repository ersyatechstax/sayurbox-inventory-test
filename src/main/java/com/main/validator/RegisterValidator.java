package com.main.validator;

import com.main.vo.RegisterVO;
import org.springframework.stereotype.Component;

/**
 * created by ersya 30/03/2019
 */
@Component
public class RegisterValidator {

    public String validateCreate(RegisterVO registerVO){

        if(!registerVO.getConfirmationPassword().equals(registerVO.getPassword())){
            return "Password and confirmation password doesn't match";
        }

        return null;
    }
}
