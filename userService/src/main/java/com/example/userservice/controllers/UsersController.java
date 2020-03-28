package com.example.userservice.controllers;

import com.example.userservice.models.UserData;
import com.example.userservice.models.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class UsersController {

    @Autowired
    private UserDataRepository repository;

    @GetMapping("/getuser")
    public ResponseEntity<UserData> getUserDetails(@RequestParam String userId){
        Optional<UserData> optionalUserData = repository.findById(userId);
        // return the user if present, or null if not
        return new ResponseEntity<>(optionalUserData.orElse(null), HttpStatus.OK);
    }

    @GetMapping("/getusernames")
    public ResponseEntity<List<String>> getAllUserNames(@RequestParam(value = "sorted", required = false) boolean sorted){
        List<UserData> users = repository.findAll();

        // check if the query returned a value
        if (sorted)
            Collections.sort(users);

        // extract all user names with java stream
        List<String> userNames = users.stream().map(UserData::getName).collect(Collectors.toList());
        return new ResponseEntity<>(userNames, HttpStatus.OK);
    }


    @PostMapping(path = "/adduser", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> addUser(@RequestBody UserData user){
        // this UUID generating logic can be extracted to service but for now it will be very small service
        UUID uuid = UUID.randomUUID();
        String _id = uuid.toString();
        user.setId(_id);

        repository.save(user);
        return new ResponseEntity<>(_id, HttpStatus.OK);
    }
}

