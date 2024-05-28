package com.bankinc.cardmanagement.dto;

import lombok.Data;

@Data
public class AnnulTransactionRequestDTO {
    private String cardId;
    private String transactionId;
}
