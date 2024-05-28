package com.bankinc.cardmanagement.service.impl;

import com.bankinc.cardmanagement.controller.CardController;
import com.bankinc.cardmanagement.entity.Card;
import com.bankinc.cardmanagement.entity.Transaction;
import com.bankinc.cardmanagement.repository.CardRepository;
import com.bankinc.cardmanagement.repository.TransactionRepository;
import com.bankinc.cardmanagement.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction makePurchase(String cardId, double amount) throws Exception {
        Card card = cardRepository.findByCardId(cardId);
        logger.info("Esta es la tarjeta: {}", card);

        if (card == null) {
            throw new Exception("Tarjeta no encontrada");
        }
        if (!card.isActive() || card.isBlocked()) {
            throw new Exception("Tarjeta inactiva o bloqueada");
        }
        if (card.getExpirationDate().isBefore(LocalDateTime.now().toLocalDate())) {
            throw new Exception("Tarjeta vencida");
        }
        if (card.getBalance() < amount || card.getBalance() == 0) {
            throw new Exception("Saldo insuficiente");
        }

        card.setBalance(card.getBalance() - amount);
        cardRepository.save(card);

        Transaction transaction = new Transaction();
        transaction.setCard(card);
        transaction.setAmount(amount);
        transaction.setCreationDate(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction getTransactionById(String transactionId) throws Exception {
        Optional<Transaction> transaction = transactionRepository.findById(Long.valueOf(transactionId));
        return transaction.orElseThrow(() -> new Exception("Transaccion no encontrada"));
    }

    @Override
    public Transaction annulTransaction(String cardId, String transactionId) throws Exception {
        Transaction transaction = getTransactionById(transactionId);
        if (transaction == null || !transaction.getCard().getCardId().equals(cardId)) {
            throw new Exception("Transacción no encontrada o tarjeta no válida");
        }
        if (transaction.getCreationDate().isBefore(LocalDateTime.now().minusHours(24))) {
            throw new Exception("La transacción no se puede anular después de 24 horas.");
        }
        if (transaction.isAnulled()) {
            throw new Exception("La transacción ya está anulada");
        }
        Card card = transaction.getCard();
        card.setBalance(card.getBalance() + transaction.getAmount());
        cardRepository.save(card);

        transaction.setAnulled(true);
        return transactionRepository.save(transaction);
    }
}