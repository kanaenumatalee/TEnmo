package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferStatusService {
    private final String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();

    public TransferStatusService(String baseUrl) {
        this.baseUrl = baseUrl + "transfer/";
    }

    public TransferStatus getTransferStatus(AuthenticatedUser authenticatedUser, String description) {
        TransferStatus transferStatus = null;
        HttpEntity entity = makeEntity(authenticatedUser);
        try {
            String url = baseUrl + "transfer_status/desc/" + description;
            transferStatus = restTemplate.exchange(url,
                                                   HttpMethod.GET,
                                                   entity,
                                                   TransferStatus.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("Failed to complete request. Code: " + e.getRawStatusCode());
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            System.out.println("Failed to complete request due to server network issues. Please try again.");
            BasicLogger.log(e.getMessage());
        }
        return transferStatus;
    }


    public TransferStatus getTransferStatusById(AuthenticatedUser authenticatedUser, int transferStatusId) {
        TransferStatus transferStatus = null;
        HttpEntity entity = makeEntity(authenticatedUser);

        try {
            String url = baseUrl + "transfer_status/" + transferStatusId;
            transferStatus = restTemplate.exchange(url,
                                                   HttpMethod.GET,
                                                   entity,
                                                   TransferStatus.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("Failed to complete request. Code: " + e.getRawStatusCode());
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            System.out.println("Failed to complete request due to server network issues. Please try again.");
            BasicLogger.log(e.getMessage());
        }

        return transferStatus;
    }

    private HttpEntity makeEntity(AuthenticatedUser authenticatedUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }
}
