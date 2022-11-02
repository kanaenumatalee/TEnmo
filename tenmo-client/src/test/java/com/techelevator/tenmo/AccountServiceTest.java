package com.techelevator.tenmo;


import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AccountService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class AccountServiceTest {
    private static final String EXPECTED_API_URL = "http://localhost:8080/account";
    private static final RestClientResponseException REST_CLIENT_RESPONSE_EXCEPTION =
            new RestClientResponseException("This is a RestClientResponseException", 0, "Testing", null, null, null);
    private static final ResourceAccessException RESOURCE_ACCESS_EXCEPTION =
            new ResourceAccessException("This is a ResourceAccessException");
    private RestTemplate mockRestTemplate;
    private AccountService sut = new AccountService(EXPECTED_API_URL);
    private User testUser = new User();
    private UserCredentials userCred = new UserCredentials("testuser", "testpass");
    private AuthenticatedUser authUser = new AuthenticatedUser();
    private Account newAccount = new Account();
    private BigDecimal returnBalance;

    @Before
    public void setup(){
        testUser.setUsername("testuser");
        testUser.setId(1002L);
        authUser.setUser(testUser);
        authUser.setToken("abcdefgh123456789");
        newAccount.setAccountId(2002);
        newAccount.setUserId(1002);
        newAccount.setBalance(BigDecimal.valueOf(1000));
    }

    @Test
    public void return_get_balance(){
        returnBalance = sut.getBalance(authUser);
        Assert.assertEquals("AccountService.getBalance() should call the API and return the balance", 1000, returnBalance);
    }

    @Test
    public void get_balance_returns_null_for_failure_response_code(){
        when(mockRestTemplate.postForObject(
                Mockito.eq(EXPECTED_API_URL),
                any(HttpEntity.class),
                Mockito.eq(Account.class)))
                .thenThrow(REST_CLIENT_RESPONSE_EXCEPTION);
        returnBalance = sut.getBalance(authUser);
        Assert.assertNull("AccountService.getBalance() should return null when RestTemplate throws a RestClientResponseException",returnBalance);
        // Since the provided method stub returns null, only let the test pass if the API was called.
        verify(mockRestTemplate)
                .postForObject(eq(EXPECTED_API_URL), any(HttpEntity.class), eq(Account.class));
    }

    @Test
    public void get_balance_returns_null_for_communication_failure() {
        // Arrange
        when(mockRestTemplate.postForObject(
                Mockito.eq(EXPECTED_API_URL),
                any(HttpEntity.class),
                Mockito.eq(Account.class)
        ))
        .thenThrow(RESOURCE_ACCESS_EXCEPTION);
        // Act
        returnBalance = sut.getBalance(authUser);
        // Assert
        Assert.assertNull(
            "auctionService.getBalance() should return null when RestTemplate throws a ResourceAccessException", 
            returnBalance
        );
        verify(mockRestTemplate)
        .postForObject(
            eq(EXPECTED_API_URL), 
            any(HttpEntity.class), 
            eq(Account.class)
        );
    }
}
