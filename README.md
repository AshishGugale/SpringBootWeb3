# Project Setup Guide
## 1️⃣ Prerequisites

Ensure you have the following installed:
- Java 17+
- Spring Boot
- Web3j CLI
- Solidity Compiler (solc)

## 2️⃣ Compiling the Smart Contract

Before using the contract in the Spring Boot backend, compile it to generate the ABI (Application Binary Interface):

```shell
solc --abi --bin -o output/ path/to/YourContract.sol
```

This generates:
•	YourContract.abi → ABI file
•	YourContract.bin → Bytecode file

## 3️⃣ Generating Java Wrapper using Web3j

Web3j provides a wrapper to interact with the contract in Java. Run:
```shell
web3j generate solidity \
--abiFile=src/main/resources/contract/YourContract.abi \
--outputDir=src/main/java/ \
--package=com.example.SpringBoot
```
This generates a Java wrapper class inside src/main/java/com/example/SpringBoot.

## 4️⃣ Configuring Application Properties

Set up the application.properties file (or use environment variables):
```properties
web3j.network-url=${WEB3_CLIENT_ADDRESS}
web3j.private-key=${WEB3_PRIVATE_KEY}
web3j.contract-address=${WEB3_CONTRACT_ADDRESS}
```

To set these, you can add these to your environment using run configurations or directly export in your terminal

## 5️⃣ Running the Spring Boot Backend

Start the application with:
```shell
./mvnw spring-boot:run
```