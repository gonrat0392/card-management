package com.bankinc.cardmanagement.controller;

import com.bankinc.cardmanagement.dto.*;
import com.bankinc.cardmanagement.service.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CardControllerTest {

    @InjectMocks
    private CardController cardController;

    @Mock
    private CardService cardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateCard() {
        GenerateCardRequestDTO requestDTO = new GenerateCardRequestDTO();
        requestDTO.setLastname("Doe");
        requestDTO.setIdProduct("123");

        when(cardService.generateCard(any(GenerateCardRequestDTO.class))).thenThrow(new RuntimeException("Error generating card"));

        ResponseEntity<ResponseDTO> response = cardController.generateCard(requestDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error generating card", response.getBody().getDescripcion());
        verify(cardService, times(1)).generateCard(any(GenerateCardRequestDTO.class));
    }

    @Test
    void testActivateCard() {
        ActivateCardRequestDTO requestDTO = new ActivateCardRequestDTO();
        requestDTO.setCardId("card123");

        when(cardService.activateCard(anyString())).thenThrow(new RuntimeException("Error activating card"));

        ResponseEntity<ResponseDTO> response = cardController.activateCard(requestDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error activating card", response.getBody().getDescripcion());
        verify(cardService, times(1)).activateCard(anyString());
    }

    @Test
    void testBalanceCard() {
        BalanceCardRequestDTO requestDTO = new BalanceCardRequestDTO();
        requestDTO.setCardId("card123");
        requestDTO.setAmount(100.0);

        when(cardService.balanceCard(anyString(), anyDouble())).thenThrow(new RuntimeException("Error updating balance"));

        ResponseEntity<ResponseDTO> response = cardController.balanceCard(requestDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error updating balance", response.getBody().getDescripcion());
        verify(cardService, times(1)).balanceCard(anyString(), anyDouble());
    }

    @Test
    void testBlockCard() throws Exception {
        String cardId = "card123";

        when(cardService.blockCard(anyString())).thenThrow(new RuntimeException("Error blocking card"));

        ResponseEntity<ResponseDTO> response = cardController.blockCard(cardId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error blocking card", response.getBody().getDescripcion());
        verify(cardService, times(1)).blockCard(anyString());
    }

    @Test
    void testCheckBalance() throws Exception {
        String cardId = "card123";

        when(cardService.checkBalance(anyString())).thenThrow(new RuntimeException("Error checking balance"));

        ResponseEntity<ResponseDTO> response = cardController.checkBalance(cardId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error checking balance", response.getBody().getDescripcion());
        verify(cardService, times(1)).checkBalance(anyString());
    }
}