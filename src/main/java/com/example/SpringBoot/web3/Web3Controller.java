package com.example.SpringBoot.web3;

import com.example.SpringBoot.web3.dto.ProductCountResponse;
import com.example.SpringBoot.web3.dto.ProductRegistrationEvent;
import com.example.SpringBoot.web3.dto.RegisterProductResponse;
import org.springframework.web.bind.annotation.*;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/api/web3")
public class Web3Controller {

    private final Web3Service web3Service;

    public Web3Controller(Web3Service web3Service) {
        this.web3Service = web3Service;
    }

    @GetMapping("/product-count")
    public ProductCountResponse getProductCount() throws Exception {
        BigInteger productCount = web3Service.getProductCount();
        return new ProductCountResponse("SUCCESS", productCount);
    }

    @PostMapping("/register-product")
    @ResponseBody
    public RegisterProductResponse registerProduct(@RequestParam String name, @RequestParam BigInteger price) throws Exception {
        TransactionReceipt tx = web3Service.registerProduct(name, price);
        return new RegisterProductResponse("SUCCESS", tx.getGasUsed(), tx.getBlockNumber());
    }

    @GetMapping("/registration-events")
    @ResponseBody
    public List<ProductRegistrationEvent> getAllProductRegisteredEvents() throws Exception {
        return web3Service.getAllProductRegisteredEvents();
    }
}