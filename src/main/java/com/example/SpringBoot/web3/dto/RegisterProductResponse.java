package com.example.SpringBoot.web3.dto;

import java.math.BigInteger;

public class RegisterProductResponse {
    private final String status;
    private final BigInteger gasUsed;
    private final BigInteger blockNumber;

    public RegisterProductResponse(String status, BigInteger gasUsed, BigInteger blockNumber) {
        this.status = status;
        this.gasUsed = gasUsed;
        this.blockNumber = blockNumber;
    }

    public String getStatus() {
        return status;
    }

    public BigInteger getGasUsed() {
        return gasUsed;
    }

    public BigInteger getBlockNumber() {
        return blockNumber;
    }
}
