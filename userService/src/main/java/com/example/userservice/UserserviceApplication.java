package com.example.userservice;

import com.example.userservice.models.UserData;
import com.example.userservice.models.UserDataRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class UserserviceApplication implements CommandLineRunner {

    @Autowired
    private UserDataRepository repository;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(UserserviceApplication.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", "8083"));
        app.run(args);
    }

    @Override
    public void run(String[] args) throws IOException {

        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        //read json file and convert to customer object
        List<UserData> usersList = objectMapper.readValue(new File("users.json"), new TypeReference<List<UserData>>(){});

        // clean the db and save the users from the json
        repository.deleteAll();
        for (UserData user: usersList){
            repository.save(user);
        }
    }

}
