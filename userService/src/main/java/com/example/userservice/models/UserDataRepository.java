package com.example.userservice.models;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserDataRepository extends MongoRepository<UserData, String> {
    public UserData findByName(String name);
    public List<UserData> findByLastName(String lastName);

}
