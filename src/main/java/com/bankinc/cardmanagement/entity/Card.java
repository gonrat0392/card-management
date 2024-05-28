package com.bankinc.cardmanagement.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Card {

    @Id
    @Column(nullable = false, unique = true)
    @JsonProperty("numeroTarjeta")
    private String cardId;

    @Column(nullable = false)
    @JsonProperty("nombreTitular")
    private String holdername;

    @Column(nullable = false)
    @JsonProperty("fechaExpiracion")
    private LocalDate expirationDate;

    @Column(nullable = false)
    @JsonProperty("saldo")
    private double balance;

    @Column(nullable = false)
    @JsonProperty("activo")
    private boolean active;

    @Column(nullable = false)
    @JsonProperty("bloqueado")
    private boolean blocked;
}
