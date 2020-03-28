package com.example.restservice;

import com.example.userservice.UserServiceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({UserServiceConfiguration.class})
public class RestServiceConfiguration {
}
