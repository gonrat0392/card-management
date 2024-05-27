package com.bankinc.cardmanagement.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonProperty("tarjeta")
    private Card card;

    @Column(nullable = false)
    @JsonProperty("monto")
    private double amount;

    @Column(nullable = false)
    @JsonProperty("fechaCreacion")
    private LocalDateTime creationDate;

    @Column(nullable = false)
    @JsonProperty("anulado")
    private boolean anulled;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isAnulled() {
        return anulled;
    }

    public void setAnulled(boolean anulled) {
        this.anulled = anulled;
    }
}