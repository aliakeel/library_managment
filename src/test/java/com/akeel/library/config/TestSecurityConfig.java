package com.akeel.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@Configuration
@TestExecutionListeners(listeners = {
        DependencyInjectionTestExecutionListener.class,
        WithSecurityContextTestExecutionListener.class
})
public class TestSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
                .csrf(csrf -> csrf.disable());
        return http.build();
    }
}
