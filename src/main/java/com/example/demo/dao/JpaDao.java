package com.example.demo.dao;

import com.example.demo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDao extends JpaRepository<Account, Integer> {

}
