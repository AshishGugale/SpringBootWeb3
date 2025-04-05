package com.example.SpringBoot.web3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

@Service
public class Web3Service {

    private final Web3j web3j;
    private final Credentials credentials;
    private final Contract contract;

    public Web3Service(Web3j web3j,
                       @Value("${web3j.private-key}") String privateKey,
                       @Value("${web3j.contract-address}") String contractAddress) {
        this.web3j = web3j;
        this.credentials = Credentials.create(privateKey);

        ContractGasProvider gasProvider = new StaticGasProvider(BigInteger.valueOf(20000000000L), BigInteger.valueOf(6721975));

        this.contract = Contract.load(contractAddress, web3j, credentials, gasProvider);
    }

    public BigInteger getProductCount() throws Exception {
        return contract.productCount().send();
    }

    public TransactionReceipt registerProduct(String name, BigInteger price) throws Exception {
        return contract.registerProduct(name, price).send();
    }
}