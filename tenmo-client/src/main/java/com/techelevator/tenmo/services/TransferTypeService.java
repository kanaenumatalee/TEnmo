package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.TransferType;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferTypeService {
    private final String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();

    public TransferTypeService(String baseUrl) {
        this.baseUrl = baseUrl + "transfer/";
    }

    public TransferType getTransferTypeByDescription(AuthenticatedUser authenticatedUser, String description) {
        TransferType transferType = null;

        try {
            String url = baseUrl + "transfer_type/filter?description=" + description;
            transferType = restTemplate.exchange(url, HttpMethod.GET, makeEntity(authenticatedUser),
                           TransferType.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("Failed to complete request. Code: " + e.getRawStatusCode());
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            System.out.println("Failed to complete request due to server network issues. Please try again.");
            BasicLogger.log(e.getMessage());
        }

        return transferType;
    }


    public TransferType getTransferTypeById(AuthenticatedUser authenticatedUser, int transferTypeId) {
        TransferType transferType = null;

        try {
            String url = baseUrl + "transfer_type/" + transferTypeId;
            transferType = restTemplate.exchange(url, HttpMethod.GET,
                           makeEntity(authenticatedUser), TransferType.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("Failed to complete request. Code: " + e.getRawStatusCode());
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            System.out.println("Failed to complete request due to server network issues. Please try again.");
            BasicLogger.log(e.getMessage());
        }

        return transferType;
    }




        private HttpEntity makeEntity(AuthenticatedUser authenticatedUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }
}
