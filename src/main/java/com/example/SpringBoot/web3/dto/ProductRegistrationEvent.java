package com.example.SpringBoot.web3.dto;

public class ProductRegistrationEvent {
    private final String transactionHash;
    private final String eventId;
    private final String productName;
    private final String owner;

    public ProductRegistrationEvent(String transactionHash, String eventId, String productName, String owner) {
        this.transactionHash = transactionHash;
        this.eventId = eventId;
        this.productName = productName;
        this.owner = owner;
    }

    public String getEventId() {
        return eventId;
    }

    public String getProductName() {
        return productName;
    }

    public String getOwner() {
        return owner;
    }

    public String getTransactionHash() {
        return transactionHash;
    }
}
