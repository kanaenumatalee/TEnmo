package com.techelevator.tenmo.services;

import org.springframework.web.client.RestTemplate;

public class TransferTypeService {
    private final String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();

    public TransferTypeService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    //getTransferType
    //getTransferTypeFromId
    //makeEntity
}
