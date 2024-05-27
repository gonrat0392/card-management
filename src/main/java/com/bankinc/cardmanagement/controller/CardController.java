package com.bankinc.cardmanagement.controller;

import com.bankinc.cardmanagement.dto.ActivateCardRequestDTO;
import com.bankinc.cardmanagement.dto.BalanceCardRequestDTO;
import com.bankinc.cardmanagement.dto.ResponseDTO;
import com.bankinc.cardmanagement.dto.GenerateCardRequestDTO;
import com.bankinc.cardmanagement.entity.Card;
import com.bankinc.cardmanagement.service.CardService;
import com.bankinc.cardmanagement.util.constant.ConstantsUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Validated
@RequestMapping("/card")
public class CardController {
    private static final Logger logger = LoggerFactory.getLogger(CardController.class);
    @Autowired
    private CardService cardService;

    /**
     * endpoint para generar una nueva tarjeta
     * @param request
     * @return
     */
    @PostMapping("/generate")
    public ResponseEntity<ResponseDTO> generateCard(@Valid @RequestBody GenerateCardRequestDTO request) {
        try {
            logger.info("ENDPOINT generateCard: {}", request.getLastname() + " " + request.getIdProduct());
            Card card = cardService.generateCard(request);
            return ResponseEntity.ok(new ResponseDTO(
                    ConstantsUtil.CODE_SUCCESS, ConstantsUtil.CARD_GENERATE_SUCCESS, card));
        } catch (Exception ex) {
            logger.error("Error en generateCard: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(
                    ConstantsUtil.CODE_ERROR, ex.getMessage(), null));
        }
    }

    /**
     * endpoint para activar una tarjeta
     * @param request
     * @return
     */
    @PostMapping("/enroll")
    public ResponseEntity<ResponseDTO> activateCard(@Valid @RequestBody ActivateCardRequestDTO request) {
        try {

            logger.info("ENDPOINT activateCard: {}", request.getCardId());
            Card card = cardService.activateCard(request.getCardId());
            return ResponseEntity.ok(new ResponseDTO(
                    ConstantsUtil.CODE_SUCCESS, ConstantsUtil.CARD_ACTIVATE_SUCCESS, card.isActive()));
        } catch (Exception ex) {
            logger.error("Error en generateCard: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(
                    ConstantsUtil.CODE_ERROR, ex.getMessage(), null));
        }
    }

    /**
     * endpoint para agregar saldo una tarjeta
     * @param request
     * @return
     */
    @PostMapping("/balance")
    public ResponseEntity<ResponseDTO> balanceCard(@Valid @RequestBody BalanceCardRequestDTO request) {
        try {
            logger.info("ENDPOINT balanceCard: {}", request.getCardId() + " " + request.getAmount());
            Card card = cardService.balanceCard(request.getCardId(), request.getAmount());
            return ResponseEntity.ok(new ResponseDTO(
                    ConstantsUtil.CODE_SUCCESS, ConstantsUtil.CARD_BALANCE_SUCCESS, card.getBalance()));
        } catch (Exception ex) {
            logger.error("Error en generateCard: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(
                    ConstantsUtil.CODE_ERROR, ex.getMessage(), null));
        }
    }

    /**
     * endpoint para bloquear una tarjeta
     * @param cardId
     * @return
     * @throws Exception
     */
    @DeleteMapping("/{cardId}")
    public ResponseEntity<ResponseDTO> blockCard(@PathVariable String cardId) throws Exception {
        try {
            logger.info("ENDPOINT balanceCard: {}", cardId );
            Card card = cardService.blockCard(cardId);
            return ResponseEntity.ok(new ResponseDTO(
                    ConstantsUtil.CODE_SUCCESS, ConstantsUtil.CARD_BLOCK_SUCCESS, card.isBlocked()));
        } catch (Exception ex) {
            logger.error("Error en generateCard: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(
                    ConstantsUtil.CODE_ERROR, ex.getMessage(), null));
        }
    }

    /**
     * endpoint para consultar el saldo de una tarjeta
     * @param cardId
     * @return
     * @throws Exception
     */
    @GetMapping("/balance/{cardId}")
    public ResponseEntity<ResponseDTO> checkBalance(@PathVariable String cardId) throws Exception {
        try {
            logger.info("ENDPOINT balanceCard: {}", cardId );
            Card card = cardService.checkBalance(cardId);
            return ResponseEntity.ok(new ResponseDTO(
                    ConstantsUtil.CODE_SUCCESS, ConstantsUtil.CARD_CHECK_SUCCESS, card.getBalance()));
        } catch (Exception ex) {
            logger.error("Error en generateCard: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(
                    ConstantsUtil.CODE_ERROR, ex.getMessage(), null));
        }
    }
}
