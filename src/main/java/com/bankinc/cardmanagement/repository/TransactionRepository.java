package com.bankinc.cardmanagement.repository;

import com.bankinc.cardmanagement.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}