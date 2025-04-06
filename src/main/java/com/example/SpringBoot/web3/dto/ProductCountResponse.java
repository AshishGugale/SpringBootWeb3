package com.example.SpringBoot.web3.dto;

import java.math.BigInteger;

public class ProductCountResponse {
    private final String status;
    private final BigInteger productCount;

    public ProductCountResponse(String status, BigInteger productCount) {
        this.status = status;
        this.productCount = productCount;
    }

    public String getStatus() {
        return status;
    }

    public BigInteger getProductCount() {
        return productCount;
    }
}