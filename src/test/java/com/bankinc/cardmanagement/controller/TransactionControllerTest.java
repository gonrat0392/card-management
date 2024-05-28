package com.bankinc.cardmanagement.controller;

import com.bankinc.cardmanagement.dto.AnnulTransactionRequestDTO;
import com.bankinc.cardmanagement.dto.PurchaseRequestDTO;
import com.bankinc.cardmanagement.dto.ResponseDTO;
import com.bankinc.cardmanagement.entity.Transaction;
import com.bankinc.cardmanagement.service.TransactionService;
import com.bankinc.cardmanagement.util.constant.ConstantsUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMakePurchase() throws Exception {
        PurchaseRequestDTO requestDTO = new PurchaseRequestDTO();
        requestDTO.setCardId("card123");
        requestDTO.setAmount(100.0);

        Transaction transaction = new Transaction();
        when(transactionService.makePurchase(anyString(), anyDouble())).thenReturn(transaction);

        ResponseEntity<ResponseDTO> response = transactionController.makePurchase(requestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ConstantsUtil.CODE_SUCCESS, response.getBody().getCodigo());
        verify(transactionService, times(1)).makePurchase(anyString(), anyDouble());
    }

    @Test
    void testGetTransaction() throws Exception {
        String transactionId = "trans123";

        Transaction transaction = new Transaction();
        when(transactionService.getTransactionById(anyString())).thenReturn(transaction);

        ResponseEntity<ResponseDTO> response = transactionController.getTransaction(transactionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ConstantsUtil.CODE_SUCCESS, response.getBody().getCodigo());
        verify(transactionService, times(1)).getTransactionById(anyString());
    }

    @Test
    void testAnnulTransaction() throws Exception {
        AnnulTransactionRequestDTO requestDTO = new AnnulTransactionRequestDTO();
        requestDTO.setCardId("card123");
        requestDTO.setTransactionId("trans123");

        Transaction transaction = new Transaction();
        transaction.setAnulled(true);
        when(transactionService.annulTransaction(anyString(), anyString())).thenReturn(transaction);

        ResponseEntity<ResponseDTO> response = transactionController.annulTransaction(requestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ConstantsUtil.CODE_SUCCESS, response.getBody().getCodigo());
        verify(transactionService, times(1)).annulTransaction(anyString(), anyString());
    }
}