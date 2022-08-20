package com.techelevator.tenmo.services;

import org.springframework.web.client.RestTemplate;

public class UserService {
    private String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();

    public UserService(String url) {
        this.baseUrl = url;
    }


    //getAllUsers
    //getUserByUserId
    //makeEntity
}
