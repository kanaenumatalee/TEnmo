package com.techelevator.tenmo.services;

import org.springframework.web.client.RestTemplate;

public class TransferStatusService {
    private final String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();

    public TransferStatusService(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
