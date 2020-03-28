package com.example.userservice.services;

import com.example.userservice.models.UserData;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class UserService {

    String domainUrl = "http://localhost:8083";

    /**
     * responsible for retrieving the user details
     * @param userId is the id of the wanted user
     * @return the user with the given id or null if not found
     */
    public UserData getUserDetailsByUserId(String userId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = this.domainUrl + "/getuser";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("userId", userId);

        ResponseEntity<UserData> response = restTemplate.getForEntity(builder.toUriString(), UserData.class);

        return response.getBody();
    }

    /**
     * responsible for retrieving all the user names (sorted by birth date - depends on sorted param)
     * @param sorted boolean that will determine either the user names will be sorted by birth date or not
     * @return list of all the user names
     */
    public List<String> getUserNames(boolean sorted) {
        RestTemplate restTemplate = new RestTemplate();
        String url = this.domainUrl + "/getusernames";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("sorted", sorted);

        ResponseEntity<List<String>> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>(){});
        return response.getBody();
    }

    /**
     * responsible for adding a new user to the system
     * @param user that will be added to the system
     * @return the id that was generated for the new user
     */
    public String addUser(UserData user) {
        RestTemplate restTemplate = new RestTemplate();
        String url = this.domainUrl + "/adduser";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserData> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        return response.getBody();
    }
}
