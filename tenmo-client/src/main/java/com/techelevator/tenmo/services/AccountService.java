package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;


import java.math.BigDecimal;

public class AccountService {

    private final String baseUrl;

    private RestTemplate restTemplate = new RestTemplate();

    public AccountService(String baseUrl) {
        this.baseUrl = baseUrl + "account/";
    }

    public BigDecimal getBalance(AuthenticatedUser authenticatedUser) {
        HttpEntity entity = makeEntity(authenticatedUser);
        BigDecimal balance = null;
        try {
            balance = restTemplate.exchange(baseUrl + "balance",
                                                HttpMethod.GET,
                                                entity,
                                                BigDecimal.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("Failed to complete request. Code: " + e.getRawStatusCode());
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            System.out.println("Failed to complete request due to server network issues. Please try again.");
            BasicLogger.log(e.getMessage());
        }
        return balance;
    }


    public Account getAccountByUserId(AuthenticatedUser authenticatedUser, int userId) {

        Account account = null;
        try {
            account = restTemplate.exchange(baseUrl + "users/" + userId,
                      HttpMethod.GET,
                      makeEntity(authenticatedUser),
                      Account.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("Failed to complete request. Code: " + e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println("Failed to complete request due to server network issues. Please try again.");
        }

        return account;
    }


    public Account getAccountById(AuthenticatedUser authenticatedUser, int accountId) {
        Account account = null;
        try {
            account = restTemplate.exchange(baseUrl + "users/" + accountId,
                      HttpMethod.GET,
                      makeEntity(authenticatedUser),
                      Account.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("Failed to complete request. Code: " + e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println("Failed to complete request due to server network issues. Please try again.");
        }
        return account;
    }


    private HttpEntity makeEntity(AuthenticatedUser authenticatedUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }
}
