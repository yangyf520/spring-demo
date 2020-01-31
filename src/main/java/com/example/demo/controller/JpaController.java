package com.example.demo.controller;

import com.example.demo.entity.Account;
import com.example.demo.service.JpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class JpaController {

    @Autowired
    JpaService jpaService;

    @Cacheable("accounts")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Account> getAccounts() {
        return jpaService.findAll();
    }

    @RequestMapping(value = "/{id}/{nm}", method = RequestMethod.GET)
    public Account getAccountById(@PathVariable("id") int id, @PathVariable("nm") String name) {
        System.out.println("User :" + id + "," + name);
        return jpaService.findById(id);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public String updateAccount(@PathVariable("id") int id, @RequestParam(value = "name", required = true) String name,
                                @RequestParam(value = "money", required = true) double money) {

        return jpaService.update(id, name, money).toString();

    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String postAccount(@RequestParam(value = "name") String name,
                              @RequestParam(value = "money") double money) {

        return jpaService.save(name, money).toString();

    }
}
