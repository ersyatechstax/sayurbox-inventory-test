package com.main.services;

import com.main.config.security.JwtTokenProvider;
import com.main.enums.AccountGroup;
import com.main.exception.GeneralException;
import com.main.persistence.domain.Role;
import com.main.persistence.domain.User;
import com.main.persistence.repository.RoleRepository;
import com.main.persistence.repository.UserRepository;
import com.main.validator.RegisterValidator;
import com.main.vo.BaseVO;
import com.main.vo.LoginResponseVO;
import com.main.vo.LoginVO;
import com.main.vo.RegisterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * created by ersya 30/03/2019
 */
@Service
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    RegisterValidator registerValidator;

    public LoginResponseVO doLogin(LoginVO loginVO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginVO.getUsername(),
                        loginVO.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateTokenResponse(authentication);
    }

    public BaseVO register(RegisterVO registerVO){

        String validateVO = registerValidator.validateCreate(registerVO);
        if(validateVO != null){
            throw new GeneralException(validateVO);
        }

        if(userRepository.existsByUsername(registerVO.getUsername())){
            throw new GeneralException("Username is already registered");
        }

        //TODO: Create Account
        User user = new User();
        user.setFullName(registerVO.getFullName());
        user.setEmail(registerVO.getEmail());
        user.setUsername(registerVO.getUsername());
        user.setPassword(passwordEncoder.encode(registerVO.getPassword()));
        user.setAccountGroup(AccountGroup.ADMIN);
        List<Role> roles = new ArrayList<>();
        Role role =  roleRepository.findByName("ROLE_CUSTOMER").
                orElseThrow(() -> new GeneralException("User Role not set."));
        roles.add(role);
        user.setRoles(roles);
        user.setAccountGroup(AccountGroup.CUSTOMER);

        User result = userRepository.save(user);
        BaseVO response = new BaseVO();
        response.setId(result.getSecureId());
        response.setVersion(result.getVersion());
        return response;
    }

    public String getCurrentUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            throw new GeneralException("You are not authenticated");
        }

        return authentication.getName();
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            throw new GeneralException("You are not authenticated");
        }
        return userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new GeneralException("User doesn't exist"));
    }



}
