package com.example.userservice;

import com.example.userservice.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceConfiguration {

    @Bean
    public UserService getUserService(){
        return new UserService();
    }
}
