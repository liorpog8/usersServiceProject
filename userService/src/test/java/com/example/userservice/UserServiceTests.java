package com.example.userservice;

import com.example.userservice.models.UserData;
import com.example.userservice.models.UserDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTests {

    @Autowired
    Environment environment;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private UserDataRepository userRepository;

    private String domainUrl = "http://localhost:";

    @Test
    void getExistingUser() {
        String port = environment.getProperty("local.server.port");
        UserData userData = new UserData("Popo", "McPopo", 2000);
        Optional <UserData> opt = Optional.of(userData);
        given(userRepository.findById("6475d945-d018-42ed-a29b-8d52f3a1dcc7"))
                .willReturn(opt);

        String url = this.domainUrl + port + "/getuser";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("userId", "6475d945-d018-42ed-a29b-8d52f3a1dcc7");

        ResponseEntity<UserData> response = restTemplate.getForEntity(builder.toUriString(), UserData.class);

        assert (response.getStatusCode().equals(HttpStatus.OK));
        assert (response.getBody().equals(userData));
    }


    @Test
    void getNonExistingUser() {
        String port = environment.getProperty("local.server.port");
        Optional <UserData> opt = Optional.empty();
        given(userRepository.findById("1"))
                .willReturn(opt);

        String url = this.domainUrl + port + "/getuser";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("userId", "6475d945-d018-42ed-a29b-8d52f3a1dcc7");

        ResponseEntity<UserData> response = restTemplate.getForEntity(builder.toUriString(), UserData.class);

        assert (response.getStatusCode().equals(HttpStatus.OK));
        assert (response.getBody() == null);
    }


    @Test
    void getUnsortedUserNames() {
        String port = environment.getProperty("local.server.port");
        List<UserData> users = new ArrayList<>();
        users.add(new UserData("Popo", "McPopo", 2000));
        users.add(new UserData("Fido", "Dido", 1985));
        users.add(new UserData("Tamash", "Shandor", 1976));
        given(userRepository.findAll())
                .willReturn(users);

        String url = this.domainUrl + port + "/getusernames";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
                //.queryParam("s", "6475d945-d018-42ed-a29b-8d52f3a1dcc7");

        ResponseEntity<List<String>> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>(){});

        assert (response.getStatusCode().equals(HttpStatus.OK));

        List<String> responseUsers = response.getBody();
        for (int i = 0; i < users.size(); i++){
            assert (responseUsers.get(i).equals(users.get(i).getName()));
        }

    }


    @Test
    void getSortedUserNames() {
        String port = environment.getProperty("local.server.port");
        List<UserData> users = new ArrayList<>();
        users.add(new UserData("Popo", "McPopo", 2000));
        users.add(new UserData("Fido", "Dido", 1985));
        users.add(new UserData("Tamash", "Shandor", 1976));
        given(userRepository.findAll())
                .willReturn(users);

        String url = this.domainUrl + port + "/getusernames";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
        .queryParam("sorted", true);

        ResponseEntity<List<String>> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>(){});

        assert (response.getStatusCode().equals(HttpStatus.OK));

        List<String> responseUsers = response.getBody();
        for (int i = 0; i < users.size(); i++){
            assert (responseUsers.get(i).equals(users.get(i).getName()));
        }
    }


    @Test
    void addUser() {
        String port = environment.getProperty("local.server.port");
        UserData user = new UserData("Lior", "Pog", 1992);
        given(userRepository.save(user))
                .willReturn(user);

        String url = this.domainUrl + port + "/adduser";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserData> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        assert (response.getStatusCode().equals(HttpStatus.OK));

        String userId = response.getBody();

        user.setId(userId);
        Optional <UserData> opt = Optional.of(user);
        given(userRepository.findById(userId))
                .willReturn(opt);

        url = this.domainUrl + port + "/getuser";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("userId", userId);

        ResponseEntity<UserData> userResponse = restTemplate.getForEntity(builder.toUriString(), UserData.class);

        assert (userResponse.getStatusCode().equals(HttpStatus.OK));
        assert (userId.equals(userResponse.getBody().getId()));

    }

    @TestConfiguration
    static class TestRestTemplateAuthenticationConfiguration {

        @Bean
        public TestRestTemplate restTemplate() {
            return new TestRestTemplate();
        }
    }


//    @Bean
//    public TestRestTemplate restTemplate() {
//        return new TestRestTemplate();
//    }
}
