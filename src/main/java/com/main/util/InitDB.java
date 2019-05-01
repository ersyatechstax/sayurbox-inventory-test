package com.main.util;

import com.main.enums.AccountGroup;
import com.main.enums.RoleName;
import com.main.persistence.domain.*;
import com.main.persistence.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * created by ersya 30/03/2019
 */
@PropertySource("classpath:data.properties")
@Component
@Slf4j
public class InitDB {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TestRepository testRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ParameterRepository parameterRepository;

    @Value("#{'${role.name}'.split(',')}")
    List<String> roleNames;

    @Value("${admin.fullname}") String adminFullName;
    @Value("${admin.username}") String adminUsername;
    @Value("${admin.email}") String adminEmail;
    @Value("${admin.password}") String adminPassword;

    @Value("#{'${item.name}'.split(',')}")
    List<String> itemNames;
    @Value("#{'${item.description}'.split(',')}")
    List<String> itemDescriptions;
    @Value("#{'${item.stock}'.split(',')}")
    List<Integer> itemStocks;

    @Value("#{'${parameter.code}'.split(',')}")
    List<String> parameterCodes;
    @Value("#{'${parameter.value}'.split(',')}")
    List<String> parameterValues;
    @Value("#{'${parameter.description}'.split(',')}")
    List<String> parameterDescriptions;

    @Value("#{'${customer.fullname}'.split(',')}")
    List<String> customerFullNames;
    @Value("#{'${customer.username}'.split(',')}")
    List<String> customerUsernames;
    @Value("#{'${customer.email}'.split(',')}")
    List<String> customerEmails;
    @Value("#{'${customer.password}'.split(',')}")
    List<String> customerPasswords;

    @PostConstruct
    public void init(){
        Authentication authentication = new UsernamePasswordAuthenticationToken("SYSTEM", "SYSTEM");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        initRoles();
        initSuperdmin();
        initCustomer();
        initItem();
        initParameter();
//        initTest();
    }

    @Transactional
    public void initRoles(){
        log.info("---------- Init Roles ----------");
        List<Role> addRoles = new ArrayList<>();
        for(String roleName  : roleNames){
            if(!roleRepository.findByName(roleName).isPresent()){
                log.info("--------"+roleName + " doesn't exists, creating new one ---");
                Role role = new Role();
                role.setName(roleName);
                addRoles.add(role);
            }
        }
        if(addRoles.size() > 0){
            roleRepository.saveAll(addRoles);
            log.info("{} roles has been created", addRoles.size());
        }
        log.info("--------------------------------");
    }

    @Transactional
    public void initTest(){
        log.info("============================ INIT TEST DOMAIN DATA ============================");
        if(testRepository.findAll().size() != 100000){
            for(int i=0; i < 100000; i++){
                testRepository.save(new Test());
            }
        }

    }

    @Transactional
    public void initSuperdmin(){
        Optional<User> userFound = userRepository.findByUsername(adminUsername);
        if(!userFound.isPresent()){
            User user = new User();
            user.setFullName(adminFullName);
            user.setUsername(adminUsername);
            user.setEmail(adminEmail);
            user.setPassword(adminPassword);
            Role role = roleRepository.findByName(RoleName.ROLE_ADMIN.name()).get();
            List<Role> roles = new ArrayList<>();
            roles.add(role);
            user.setRoles(roles);
            user.setAccountGroup(AccountGroup.ADMIN);
            userRepository.save(user);
        }
    }

    @Transactional
    public void initItem(){
        for(int i = 0; i < itemNames.size(); i++){
            if(!itemRepository.findByName(itemNames.get(i)).isPresent()){
                itemRepository.save(new Item(itemNames.get(i),itemStocks.get(i),itemDescriptions.get(i)));
            }
        }
    }

    @Transactional
    public void initParameter(){
        for(int i = 0; i < parameterCodes.size(); i++){
            if(parameterRepository.findByCode(parameterCodes.get(i)) == null){
                parameterRepository.save(new Parameter(parameterCodes.get(i),parameterValues.get(i),parameterDescriptions.get(i)));
            }
        }
    }

    @Transactional
    public void initCustomer(){
        log.info("Init customer:");
        for(int i = 0; i < customerFullNames.size(); i++){
            if(!userRepository.findByUsername(customerUsernames.get(i)).isPresent()){
                User user = new User();
                user.setFullName(customerFullNames.get(i));
                user.setUsername(customerUsernames.get(i));
                user.setEmail(customerEmails.get(i));
                user.setPassword(customerPasswords.get(i));
                user.setRoles(roleRepository.findByNameEquals(RoleName.ROLE_CUSTOMER.name()));
                user.setAccountGroup(AccountGroup.CUSTOMER);
                userRepository.save(user);
            }
        }
    }
}
