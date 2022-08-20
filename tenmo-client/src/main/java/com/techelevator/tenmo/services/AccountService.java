package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;


import java.math.BigDecimal;

public class AccountService {
    //*jaron
    public static final String API_BASE_URL = "http://localhost:8080/account";
    //*jaron
    private RestTemplate restTemplate = new RestTemplate();
    //*jaron
    public String authenticationToken = null;

    //*jaron
    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    //*jaron/kanae
    public BigDecimal getBalance() {
//brackpoint below on line 29
        BigDecimal theBalance = new BigDecimal(1000);
        try {

            theBalance = restTemplate.exchange(API_BASE_URL+"/balance", HttpMethod.GET, makeEntity(), BigDecimal.class).getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }


        return theBalance;
    }

    //*jaron/kanae
    private HttpEntity makeEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.authenticationToken);
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }
}
