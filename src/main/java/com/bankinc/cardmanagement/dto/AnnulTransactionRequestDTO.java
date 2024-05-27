package com.bankinc.cardmanagement.dto;

public class AnnulTransactionRequestDTO {
    private String cardId;
    private String transactionId;

    public AnnulTransactionRequestDTO() {
    }

    public AnnulTransactionRequestDTO(String cardId, String transactionId) {
        this.cardId = cardId;
        this.transactionId = transactionId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
