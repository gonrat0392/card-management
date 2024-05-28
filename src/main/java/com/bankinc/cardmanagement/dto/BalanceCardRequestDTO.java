package com.bankinc.cardmanagement.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BalanceCardRequestDTO {
    @NotBlank(message = "El numero de tarjeta no puede ser nulo o vacío")
    @Size(min = 16, max = 16, message = "El numero de tarjeta debe tener 16 dígitos")
    private String cardId;
    @NotNull(message = "El monto no puede ser nulo")
    @Positive(message = "El monto debe ser mayor que cero")
    private double amount;
}
