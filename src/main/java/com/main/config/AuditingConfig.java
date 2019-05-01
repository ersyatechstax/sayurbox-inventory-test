package com.main.config;

import com.main.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

/**
 * created by ersya 28/04/2019
 */
@Configuration
@EnableJpaAuditing
public class AuditingConfig{

    @Autowired
    AuthService authService;

    @Bean
    public AuditorAware<String> auditorProvider(){
        return new AuditorAware<String>() {
            @Override
            public Optional<String> getCurrentAuditor() {
                return Optional.ofNullable(authService.getCurrentUsername());
            }
        };
    }
}
