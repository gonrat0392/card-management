package com.bankinc.cardmanagement.service.impl;

import com.bankinc.cardmanagement.dto.GenerateCardRequestDTO;
import com.bankinc.cardmanagement.entity.Card;
import com.bankinc.cardmanagement.repository.CardRepository;
import com.bankinc.cardmanagement.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    /**
     * generateCard()
     * Permite generar una nueva tarjeta por el holdername y idProduct
     * @param request
     * @return
     */
    @Override
    public Card generateCard(GenerateCardRequestDTO request) {
        String numberCard = generateNumberCard(request.getIdProduct());
        LocalDate fechaVencimiento = LocalDate.now().plusYears(3);
        Card card = new Card();
        card.setCardId(numberCard);
        card.setHoldername(request.getName() + " " + request.getLastname());
        card.setExpirationDate(fechaVencimiento);
        card.setBalance(0);
        card.setActive(false);
        card.setBlocked(false);
        return cardRepository.save(card);
    }

    /**
     * activateCard()
     * Activar tarjeta por el idCard
     * @param idCard
     * @return
     */
    @Override
    public Card activateCard(String idCard) {
        Card card = cardRepository.findByCardId(idCard);
        card.setActive(true);
        return cardRepository.save(card);
    }

    /**
     * balanceCard()
     * Permite consultar el saldo de la tarjeta por el idCard y amount
     * @param idCard
     * @param amount
     * @return
     */
    @Override
    public Card balanceCard(String idCard, double amount) {
        Card card = cardRepository.findByCardId(idCard);
        card.setBalance(card.getBalance() + amount);
        return cardRepository.save(card);
    }

    /**
     * generateNumberCard()
     * Permite generar un numero de tarjeta por el idProduct
     * @param idProduct
     * @return
     */
    @Override
    public String generateNumberCard(String idProduct) {
        Random random = new Random();
        StringBuilder numeroTarjeta = new StringBuilder(idProduct);
        for (int i = 0; i < 10; i++) {
            numeroTarjeta.append(random.nextInt(10));
        }
        return numeroTarjeta.toString();
    }

    /**
     * blockCard()
     * Permite bloquear una tarjeta por el cardId
     * @param cardId
     * @return
     * @throws Exception
     */
    @Override
    public Card blockCard(String cardId) throws Exception {
        Card card = getCardByNumber(cardId);
        if (card == null) {
            throw new Exception("Tarjeta no encontrada");
        }
        card.setBlocked(true);
        return cardRepository.save(card);
    }

    /**
     * checkBalance()
     * Permite consultar el saldo de una tarjeta por el cardId
     *
     * @param cardId
     * @return
     * @throws Exception
     */
    @Override
    public Card checkBalance (String cardId) throws Exception {
        Card card = getCardByNumber(cardId);
        if (card == null) {
            throw new Exception("Tarjeta no encontrada");
        }
        return card;
    }

    /**
     * getCardByNumber()
     * Permite consultar una tarjeta por el cardId
     * @param cardId
     * @return
     */
    private Card getCardByNumber(String cardId) {
        Optional<Card> optionalTarjeta = Optional.ofNullable(cardRepository.findByCardId(cardId));
        return optionalTarjeta.orElse(null);
    }
}