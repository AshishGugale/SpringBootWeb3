// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract SupplyChain {
    struct Product {
        uint id;
        string name;
        address owner;
        string status;
    }

    mapping(uint => Product) public products;
    uint public productCount;

    event ProductRegistered(uint id, string name, address owner);
    event OwnershipTransferred(uint id, address newOwner, string status);

    function registerProduct(string memory _name) public {
        productCount++;
        products[productCount] = Product(productCount, _name, msg.sender, "Manufactured");
        emit ProductRegistered(productCount, _name, msg.sender);
    }

    function transferOwnership(uint _id, address _newOwner, string memory _status) public {
        require(products[_id].owner == msg.sender, "Not owner");
        products[_id].owner = _newOwner;
        products[_id].status = _status;
        emit OwnershipTransferred(_id, _newOwner, _status);
    }

    function getProduct(uint _id) public view returns (uint, string memory, address, string memory) {
        Product memory p = products[_id];
        return (p.id, p.name, p.owner, p.status);
    }
}