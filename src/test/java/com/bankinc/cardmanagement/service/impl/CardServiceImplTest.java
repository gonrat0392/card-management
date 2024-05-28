package com.bankinc.cardmanagement.service.impl;

import com.bankinc.cardmanagement.dto.GenerateCardRequestDTO;
import com.bankinc.cardmanagement.entity.Card;
import com.bankinc.cardmanagement.repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CardServiceImplTest {

    @InjectMocks
    private CardServiceImpl cardService;

    @Mock
    private CardRepository cardRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateCard() {
        GenerateCardRequestDTO requestDTO = new GenerateCardRequestDTO();
        requestDTO.setName("John");
        requestDTO.setLastname("Doe");
        requestDTO.setIdProduct("123");

        Card card = new Card();
        card.setHoldername("John Doe"); // Configura correctamente el mock
        when(cardRepository.save(any(Card.class))).thenReturn(card);

        Card result = cardService.generateCard(requestDTO);

        assertEquals("John Doe", result.getHoldername());
        assertEquals(0, result.getBalance());
        assertEquals(false, result.isActive());
        assertEquals(false, result.isBlocked());
        verify(cardRepository, times(1)).save(any(Card.class));
    }

    @Test
    void testActivateCard() {
        String cardId = "card123";
        Card card = new Card();
        card.setCardId(cardId);
        card.setActive(false);

        when(cardRepository.findByCardId(cardId)).thenReturn(card);
        when(cardRepository.save(any(Card.class))).thenReturn(card);

        Card result = cardService.activateCard(cardId);

        assertEquals(true, result.isActive());
        verify(cardRepository, times(1)).findByCardId(cardId);
        verify(cardRepository, times(1)).save(card);
    }

    @Test
    void testBalanceCard() {
        String cardId = "card123";
        double amount = 100.0;
        Card card = new Card();
        card.setCardId(cardId);
        card.setBalance(50.0);

        when(cardRepository.findByCardId(cardId)).thenReturn(card);
        when(cardRepository.save(any(Card.class))).thenReturn(card);

        Card result = cardService.balanceCard(cardId, amount);

        assertEquals(150.0, result.getBalance());
        verify(cardRepository, times(1)).findByCardId(cardId);
        verify(cardRepository, times(1)).save(card);
    }

    @Test
    void testGenerateNumberCard() {
        String idProduct = "123";
        String numberCard = cardService.generateNumberCard(idProduct);

        assertEquals(13, numberCard.length());
        assertEquals(idProduct, numberCard.substring(0, 3));
    }

    @Test
    void testBlockCard() throws Exception {
        String cardId = "card123";
        Card card = new Card();
        card.setCardId(cardId);
        card.setBlocked(false);

        when(cardRepository.findByCardId(cardId)).thenReturn(card);
        when(cardRepository.save(any(Card.class))).thenReturn(card);

        Card result = cardService.blockCard(cardId);

        assertEquals(true, result.isBlocked());
        verify(cardRepository, times(1)).findByCardId(cardId);
        verify(cardRepository, times(1)).save(card);
    }

    @Test
    void testBlockCardNotFound() {
        String cardId = "card123";

        when(cardRepository.findByCardId(cardId)).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () -> cardService.blockCard(cardId));

        assertEquals("Tarjeta no encontrada", exception.getMessage());
        verify(cardRepository, times(1)).findByCardId(cardId);
    }

    @Test
    void testCheckBalance() throws Exception {
        String cardId = "card123";
        Card card = new Card();
        card.setCardId(cardId);
        card.setBalance(100.0);

        when(cardRepository.findByCardId(cardId)).thenReturn(card);

        Card result = cardService.checkBalance(cardId);

        assertEquals(100.0, result.getBalance());
        verify(cardRepository, times(1)).findByCardId(cardId);
    }

    @Test
    void testCheckBalanceNotFound() {
        String cardId = "card123";

        when(cardRepository.findByCardId(cardId)).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () -> cardService.checkBalance(cardId));

        assertEquals("Tarjeta no encontrada", exception.getMessage());
        verify(cardRepository, times(1)).findByCardId(cardId);
    }
}