package com.techelevator.tenmo.services;

import org.springframework.web.client.RestTemplate;

public class TransferService {

    private final String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();

    public TransferService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    //makeTransfer
    //getTransfersFromUserId
    //getTransferFromTransferId
    //getAllTransfers
    //makeEntity
    //getPendingTransfersByUserId
    //updateTransfer
}
