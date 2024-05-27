package com.bankinc.cardmanagement.service;

import com.bankinc.cardmanagement.entity.Transaction;

public interface TransactionService {
    Transaction makePurchase(String cardId, double amount) throws Exception;

    Transaction getTransactionById(String transactionId) throws Exception;

    Transaction annulTransaction(String cardId, String transactionId) throws Exception;
}
