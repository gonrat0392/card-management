package com.bankinc.cardmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class PurchaseRequestDTO {
    @NotBlank(message = "El numero de tarjeta no puede ser nulo o vacío")
    @Size(min = 16, max = 16, message = "El numero de tarjeta debe tener 16 dígitos")
    private String cardId;
    @NotNull(message = "El monto no puede ser nulo")
    @Positive(message = "El monto debe ser mayor que cero")
    private double amount;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
