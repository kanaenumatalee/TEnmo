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
        this.baseUrl = baseUrl + "transfer/";
    }


    public Transfer[] viewTransferHistory(AuthenticatedUser authenticatedUser) {
        HttpEntity entity = makeEntity(authenticatedUser);
        Transfer[] transfer= new Transfer[0];

        try {
            transfer = restTemplate.exchange(baseUrl,
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



    public void makeTransfer(AuthenticatedUser authenticatedUser, Transfer transfer) {
        HttpEntity entity = transferEntity(authenticatedUser, transfer);
        try {
            restTemplate.exchange(baseUrl + "users/" +
                                  transfer.getTransferId(),
                                  HttpMethod.POST,
                                  entity,
                                  Transfer.class);
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            System.out.println("Failed to complete request due to server network issues. Please try again.");
            BasicLogger.log(e.getMessage());
        }
    }




    public Transfer[] getTransfersByUserId(AuthenticatedUser authenticatedUser, int userId) {
        Transfer[] transfers = null;
        HttpEntity entity = makeEntity(authenticatedUser);

        try {
            transfers = restTemplate.exchange(baseUrl + "users/" +
                                              userId,
                                              HttpMethod.GET,
                                              entity,
                                              Transfer[].class).getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            System.out.println("Failed to complete request due to server network issues. Please try again.");
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }




    public Transfer getTransferByTransferId(AuthenticatedUser authenticatedUser, int transferId) {
        Transfer transfer = null;
        HttpEntity entity = makeEntity(authenticatedUser);

        try {
            transfer = restTemplate.exchange(baseUrl + transferId,
                                             HttpMethod.GET,
                                             entity,
                                             Transfer.class).getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            System.out.println("Failed to complete request due to server network issues. Please try again.");
            BasicLogger.log(e.getMessage());
        }
        return transfer;
    }




    public Transfer[] getAllTransfers(AuthenticatedUser authenticatedUser) {
        Transfer[] transfers = new Transfer[0];
        HttpEntity entity = makeEntity(authenticatedUser);

        try {
            transfers = restTemplate.exchange(baseUrl,
                                              HttpMethod.GET,
                                              entity,
                                              Transfer[].class).getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            System.out.println("Failed to complete request due to server network issues. Please try again.");
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }



    public Transfer[] getPendingTransfersByUserId(AuthenticatedUser authenticatedUser) {
        Transfer[] transfers = null;
        HttpEntity entity = makeEntity(authenticatedUser);

        try {
            transfers = restTemplate.exchange(baseUrl + "users/" +
                                              authenticatedUser.getUser().getId() + "/pending",
                                              HttpMethod.GET,
                                              entity,
                                              Transfer[].class).getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }



    public void updateTransferStatus(AuthenticatedUser authenticatedUser, Transfer transfer) {
        HttpEntity entity =  transferEntity(authenticatedUser, transfer);

        try {
            restTemplate.exchange(baseUrl + "transfers/" + transfer.getTransferId(),
                                  HttpMethod.PUT,
                                  entity,
                                  Transfer.class);
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            System.out.println("Failed to complete request due to server network issues. Please try again.");
            BasicLogger.log(e.getMessage());
        }
    }



    private HttpEntity makeEntity(AuthenticatedUser authenticatedUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }

    private HttpEntity transferEntity(AuthenticatedUser authenticatedUser, Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        return new HttpEntity(transfer, headers);
    }
}
