package com.example.userservice;

import com.example.userservice.controllers.UsersController;
import com.example.userservice.models.UserData;
import com.example.userservice.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UsersController usersController;

    @Autowired
    private UserService userService;

    @Test
    void getUser() {
//        UserData user1 = userService.getUserDetailsByUserId("blabla");
//        UserData user2 = userService.getUserDetailsByUserId("6475d945-d018-42ed-a29b-8d52f3a1dcc7");
//        assert(user1 == null);
//        assert(user2 != null && user2.getName().equals("Popo"));
    }
}
