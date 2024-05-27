package com.bankinc.cardmanagement.controller;

import com.bankinc.cardmanagement.dto.AnnulTransactionRequestDTO;
import com.bankinc.cardmanagement.dto.PurchaseRequestDTO;
import com.bankinc.cardmanagement.dto.ResponseDTO;
import com.bankinc.cardmanagement.entity.Transaction;
import com.bankinc.cardmanagement.service.TransactionService;
import com.bankinc.cardmanagement.util.constant.ConstantsUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/purchase")
    public ResponseEntity<ResponseDTO> makePurchase(@Valid @RequestBody PurchaseRequestDTO request) {
        try {
            Transaction transaction = transactionService.makePurchase(request.getCardId(), request.getAmount());
            return ResponseEntity.ok(new ResponseDTO(
                    ConstantsUtil.CODE_SUCCESS, ConstantsUtil.TRANSACTION_SUCCESS, transaction));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(
                    ConstantsUtil.CODE_ERROR, e.getMessage(), null));
        }
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<ResponseDTO> getTransaction(@PathVariable String transactionId) {
        try {
            Transaction transaction = transactionService.getTransactionById(transactionId);
            return ResponseEntity.ok(new ResponseDTO(
                    ConstantsUtil.CODE_SUCCESS, ConstantsUtil.TRANSACTION_SUCCESS, transaction));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(
                    ConstantsUtil.CODE_ERROR, e.getMessage(), null));
        }
    }

    @PostMapping("/anulation")
    public ResponseEntity<ResponseDTO> annulTransaction(@RequestBody AnnulTransactionRequestDTO request) {
        try {
            Transaction transaction = transactionService.annulTransaction(request.getCardId(), request.getTransactionId());
            return ResponseEntity.ok(new ResponseDTO(
                    ConstantsUtil.CODE_SUCCESS, ConstantsUtil.TRANSACTION_ANUL_SUCCESS, transaction.isAnulled()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(
                    ConstantsUtil.CODE_ERROR, e.getMessage(), null));
        }
    }
}