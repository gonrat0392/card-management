package com.bankinc.cardmanagement.service;

import com.bankinc.cardmanagement.dto.GenerateCardRequestDTO;
import com.bankinc.cardmanagement.entity.Card;

public interface CardService {
    Card generateCard(GenerateCardRequestDTO request);

    Card activateCard(String cardId);

    Card balanceCard(String cardId, double amount);

    String generateNumberCard(String idProduct);

    Card blockCard(String cardId) throws Exception;

    Card checkBalance(String cardId) throws Exception;
}
