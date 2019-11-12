package com.example.demo.controller;

import com.example.demo.dao.MongoDao;
import com.example.demo.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mongo")
public class MongoController {

    @Autowired
    private MongoDao mongoDao;

    @PostMapping(value = "add")
    public void addUser() {

        mongoDao.deleteAll();

        // save a couple of customers
        mongoDao.save(new Customer("Alice", "Smith"));
        mongoDao.save(new Customer("Bob", "Smith"));
    }

    @PostMapping(value = "getUser")
    public Customer getUserByName(@RequestParam String name) {
        return mongoDao.findByFirstName(name);
    }

    @PostMapping(value = "deleteUser")
    public void deleteUser(String id) {
        mongoDao.deleteById(id);
    }
}
