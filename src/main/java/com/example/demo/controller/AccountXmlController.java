package com.example.demo.controller;

import com.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/account")
public class AccountXmlController {

    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public void transfer() {
        accountService.transfer();
    }
}
