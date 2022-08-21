package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferStatusService {
    private final String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();

    public TransferStatusService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public TransferStatus getTransferStatus(AuthenticatedUser authenticatedUser, String description) {
        TransferStatus transferStatus = null;
        try {
            String url = baseUrl + "transferstatus/filter?description=" + description;
            transferStatus = restTemplate.exchange(url, HttpMethod.GET,
                             makeEntity(authenticatedUser), TransferStatus.class).getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transferStatus;
    }


    public TransferStatus getTransferStatusById(AuthenticatedUser authenticatedUser, int transferStatusId) {
        TransferStatus transferStatus = null;
        try {
            String url = baseUrl + "transferstatus/" + transferStatusId;
            transferStatus = restTemplate.exchange(url, HttpMethod.GET, makeEntity(authenticatedUser),
                             TransferStatus.class).getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return transferStatus;
    }

    private HttpEntity makeEntity(AuthenticatedUser authenticatedUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }
}
