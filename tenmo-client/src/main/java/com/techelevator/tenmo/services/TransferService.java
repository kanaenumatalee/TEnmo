package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class TransferService {

    private final String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();

    public TransferService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Transfer[] viewTransferHistory(AuthenticatedUser authenticatedUser) {
        HttpEntity entity = makeEntity(authenticatedUser);
        Transfer[] transfer= new Transfer[0];

        try {
            transfer = restTemplate.exchange(baseUrl + "/transfer",
                    HttpMethod.GET,
                    entity,
                    Transfer[].class).getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfer;
    }

    //makeTransfer
    //getTransfersFromUserId
    //getTransferFromTransferId
    //getAllTransfers
    //makeEntity
    //getPendingTransfersByUserId
    //updateTransfer

    private HttpEntity makeEntity(AuthenticatedUser authenticatedUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }
}
