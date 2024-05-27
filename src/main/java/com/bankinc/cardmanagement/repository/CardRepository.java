package com.bankinc.cardmanagement.repository;

import com.bankinc.cardmanagement.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
    Card findByCardId(String cardId);
}