package com.bankinc.cardmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ActivateCardRequestDTO {
    @NotBlank(message = "El numero de tarjeta no puede ser nulo o vacío")
    @Size(min = 16, max = 16, message = "El numero de tarjeta debe tener 16 dígitos")
    private String cardId;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
