package com.example.SpringBoot.web3;

import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/api/blockchain")
public class Web3Controller {

    private final Web3Service web3Service;

    public Web3Controller(Web3Service web3Service) {
        this.web3Service = web3Service;
    }

    @GetMapping("/product-count")
    public BigInteger getProductCount() throws Exception {
        return web3Service.getProductCount();
    }

    @PostMapping("/register-product")
    public String registerProduct(@RequestParam String name, @RequestParam BigInteger price) {
        try {
            web3Service.registerProduct(name, price);
            return "Product registered successfully";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}