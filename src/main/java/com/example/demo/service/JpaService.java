package com.example.demo.service;

import com.example.demo.dao.JpaDao;
import com.example.demo.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JpaService {

    @Autowired
    JpaDao jpaDao;

    public List<Account> findAll() {
        return jpaDao.findAll();
    }

    public Account findById(int id) {
        Optional<Account> account = jpaDao.findById(id);
        return account.get();
    }


    public Account update(int id, String name, double money) {
        Account account = new Account();
        account.setMoney(money);
        account.setName(name);
        account.setId(id);
        return jpaDao.saveAndFlush(account);
    }

    public Account save(String name, double money) {
        Account account = new Account();
        account.setMoney(money);
        account.setName(name);
        return jpaDao.save(account);
    }
}
