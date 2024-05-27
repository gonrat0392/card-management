package com.bankinc.cardmanagement.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
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

    // Getters y Setters


    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getHoldername() {
        return holdername;
    }

    public void setHoldername(String holdername) {
        this.holdername = holdername;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
