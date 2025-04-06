package com.example.SpringBoot.web3;

import com.example.SpringBoot.web3.dto.ProductRegistrationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class Web3Service {

    private static final Logger logger = LoggerFactory.getLogger(Web3Service.class);

    private final Web3j web3j;
    private final Credentials credentials;
    private final Contract contract;

    @Value("${web3j.contract-address}")
    String deployedContractAddress;

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

    public List<ProductRegistrationEvent> getAllProductRegisteredEvents() throws Exception {
        final Event event = new Event("ProductRegistered",
                Arrays.asList(
                        TypeReference.create(Uint256.class),
                        TypeReference.create(Utf8String.class),
                        TypeReference.create(Address.class)
                )
        );

        String eventSignature = EventEncoder.encode(event);

        EthFilter filter = new EthFilter(
                DefaultBlockParameterName.EARLIEST,
                DefaultBlockParameterName.LATEST,
                deployedContractAddress
        );

        filter.addSingleTopic(eventSignature);
        EthLog ethLog = web3j.ethGetLogs(filter).send();

        List<ProductRegistrationEvent> events = new ArrayList<>();

        for (EthLog.LogResult<?> logResult : ethLog.getLogs()) {
            Log log = (Log) logResult.get();
            EventValues eventValues = Contract.staticExtractEventParameters(event, log);
            if (eventValues == null) continue;
            ProductRegistrationEvent productRegistrationEvent = new ProductRegistrationEvent(
                    log.getTransactionHash(),
                    String.valueOf(eventValues.getNonIndexedValues().get(0).getValue()),
                    (String) eventValues.getNonIndexedValues().get(1).getValue(),
                    (String) eventValues.getNonIndexedValues().get(2).getValue()
            );
            events.add(productRegistrationEvent);
        }

        return events;
    }
}