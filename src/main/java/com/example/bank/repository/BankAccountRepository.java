package com.example.bank.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bank.entity.BankAccount;



@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
}


