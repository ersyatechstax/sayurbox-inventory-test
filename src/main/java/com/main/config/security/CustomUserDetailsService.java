package com.main.config.security;

import com.main.persistence.domain.User;
import com.main.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //TODO: Let people login with username
        User user = userRepository.findByUsername(username).orElseThrow(()->
                new UsernameNotFoundException("User "+ username +" not found"));
        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadedByUserId(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));
        return UserPrincipal.create(user);
    }
}
