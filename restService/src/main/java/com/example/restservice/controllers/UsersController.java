package com.example.restservice.controllers;

import com.example.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.userservice.models.UserData;

import java.util.List;

@RestController
public class UsersController {

    @Autowired
    UserService userService;

    @GetMapping("/getuser")
    public ResponseEntity<UserData> getUserDetails(@RequestParam String userId){
        UserData userData =  userService.getUserDetailsByUserId(userId);
        if (userData != null)
            return new ResponseEntity<>(userData, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getusernames")
    public ResponseEntity<List<String>> getUserNames(@RequestParam(value = "sorted", required = false) boolean sorted){
        List<String> userNames =  userService.getUserNames(sorted);
        return new ResponseEntity<>(userNames, HttpStatus.OK);
    }

    @PostMapping(path = "/adduser", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> addUser(@RequestBody UserData user){
        System.out.println(user);
        String newId = userService.addUser(user);
        return new ResponseEntity<>(newId, HttpStatus.OK);
    }
}
