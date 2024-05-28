package com.bankinc.cardmanagement.service.impl;

import com.bankinc.cardmanagement.entity.Card;
import com.bankinc.cardmanagement.entity.Transaction;
import com.bankinc.cardmanagement.repository.CardRepository;
import com.bankinc.cardmanagement.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMakePurchase() throws Exception {
        String cardId = "card123";
        double amount = 50.0;
        Card card = new Card();
        card.setCardId(cardId);
        card.setActive(true);  // La tarjeta debe estar activa para la compra
        card.setBlocked(false);  // La tarjeta no debe estar bloqueada
        card.setExpirationDate(LocalDate.now().plusYears(1));
        card.setBalance(100.0);

        when(cardRepository.findByCardId(cardId)).thenReturn(card);
        when(cardRepository.save(any(Card.class))).thenReturn(card);

        Transaction transaction = new Transaction();
        transaction.setCard(card);
        transaction.setAmount(amount);
        transaction.setCreationDate(LocalDateTime.now());

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction result = transactionService.makePurchase(cardId, amount);

        assertEquals(amount, result.getAmount());
        verify(cardRepository, times(1)).findByCardId(cardId);
        verify(cardRepository, times(1)).save(card);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void testMakePurchaseCardNotFound() {
        String cardId = "card123";
        double amount = 50.0;

        when(cardRepository.findByCardId(cardId)).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () -> transactionService.makePurchase(cardId, amount));

        assertEquals("Tarjeta no encontrada", exception.getMessage());
        verify(cardRepository, times(1)).findByCardId(cardId);
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void testMakePurchaseCardInactiveOrBlocked() {
        String cardId = "card123";
        double amount = 50.0;
        Card card = new Card();
        card.setCardId(cardId);
        card.setActive(false);
        card.setBlocked(true);

        when(cardRepository.findByCardId(cardId)).thenReturn(card);

        Exception exception = assertThrows(Exception.class, () -> transactionService.makePurchase(cardId, amount));

        assertEquals("Tarjeta inactiva o bloqueada", exception.getMessage());
        verify(cardRepository, times(1)).findByCardId(cardId);
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void testMakePurchaseCardExpired() {
        String cardId = "card123";
        double amount = 50.0;
        Card card = new Card();
        card.setCardId(cardId);
        card.setActive(true);
        card.setBlocked(false);
        card.setExpirationDate(LocalDate.now().minusDays(1));

        when(cardRepository.findByCardId(cardId)).thenReturn(card);

        Exception exception = assertThrows(Exception.class, () -> transactionService.makePurchase(cardId, amount));

        assertEquals("Tarjeta vencida", exception.getMessage());
        verify(cardRepository, times(1)).findByCardId(cardId);
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void testMakePurchaseInsufficientBalance() {
        String cardId = "card123";
        double amount = 50.0;
        Card card = new Card();
        card.setCardId(cardId);
        card.setActive(true);
        card.setBlocked(false);
        card.setExpirationDate(LocalDate.now().plusYears(1));
        card.setBalance(30.0);

        when(cardRepository.findByCardId(cardId)).thenReturn(card);

        Exception exception = assertThrows(Exception.class, () -> transactionService.makePurchase(cardId, amount));

        assertEquals("Saldo insuficiente", exception.getMessage());
        verify(cardRepository, times(1)).findByCardId(cardId);
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void testGetTransactionById() throws Exception {
        String transactionId = "1";
        Transaction transaction = new Transaction();

        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transaction));

        Transaction result = transactionService.getTransactionById(transactionId);

        assertEquals(transaction, result);
        verify(transactionRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetTransactionByIdNotFound() {
        String transactionId = "1";

        when(transactionRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> transactionService.getTransactionById(transactionId));

        assertEquals("Transaccion no encontrada", exception.getMessage());
        verify(transactionRepository, times(1)).findById(anyLong());
    }

    @Test
    void testAnnulTransaction() throws Exception {
        String cardId = "card123";
        String transactionId = "1";
        Card card = new Card();
        card.setCardId(cardId);
        card.setBalance(100.0);

        Transaction transaction = new Transaction();
        transaction.setCard(card);
        transaction.setAmount(50.0);
        transaction.setCreationDate(LocalDateTime.now());
        transaction.setAnulled(false);

        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transaction));
        when(cardRepository.save(any(Card.class))).thenReturn(card);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction result = transactionService.annulTransaction(cardId, transactionId);

        assertEquals(true, result.isAnulled());
        verify(transactionRepository, times(1)).findById(anyLong());
        verify(cardRepository, times(1)).save(card);
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    void testAnnulTransactionNotFound() {
        String cardId = "card123";
        String transactionId = "1";

        when(transactionRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> transactionService.annulTransaction(cardId, transactionId));

        assertEquals("Transaccion no encontrada", exception.getMessage());
        verify(transactionRepository, times(1)).findById(anyLong());
        verify(cardRepository, never()).save(any(Card.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void testAnnulTransactionCardMismatch() {
        String cardId = "card123";
        String transactionId = "1";
        Card card = new Card();
        card.setCardId("card456");

        Transaction transaction = new Transaction();
        transaction.setCard(card);

        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transaction));

        Exception exception = assertThrows(Exception.class, () -> transactionService.annulTransaction(cardId, transactionId));

        assertEquals("Transacción no encontrada o tarjeta no válida", exception.getMessage());
        verify(transactionRepository, times(1)).findById(anyLong());
        verify(cardRepository, never()).save(any(Card.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void testAnnulTransactionAfter24Hours() {
        String cardId = "card123";
        String transactionId = "1";
        Card card = new Card();
        card.setCardId(cardId);

        Transaction transaction = new Transaction();
        transaction.setCard(card);
        transaction.setCreationDate(LocalDateTime.now().minusDays(1).minusHours(1));

        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transaction));

        Exception exception = assertThrows(Exception.class, () -> transactionService.annulTransaction(cardId, transactionId));

        assertEquals("La transacción no se puede anular después de 24 horas.", exception.getMessage());
        verify(transactionRepository, times(1)).findById(anyLong());
        verify(cardRepository, never()).save(any(Card.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void testAnnulTransactionAlreadyAnnulled() {
        String cardId = "card123";
        String transactionId = "1";
        Card card = new Card();
        card.setCardId(cardId);

        Transaction transaction = new Transaction();
        transaction.setCard(card);
        transaction.setCreationDate(LocalDateTime.now());
        transaction.setAnulled(true);

        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transaction));

        Exception exception = assertThrows(Exception.class, () -> transactionService.annulTransaction(cardId, transactionId));

        assertEquals("La transacción ya está anulada", exception.getMessage());
        verify(transactionRepository, times(1)).findById(anyLong());
        verify(cardRepository, never()).save(any(Card.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }
}