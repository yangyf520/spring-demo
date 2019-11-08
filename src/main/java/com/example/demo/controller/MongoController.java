package com.example.demo.controller;

import com.example.demo.dao.CustomerMongoDao;
import com.example.demo.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mongo")
public class MongoController {

    @Autowired
    private CustomerMongoDao customerMongoDao;

    @PostMapping(value = "add")
    public void addUser() {

        customerMongoDao.deleteAll();

        // save a couple of customers
        customerMongoDao.save(new Customer("Alice", "Smith"));
        customerMongoDao.save(new Customer("Bob", "Smith"));
    }

}
