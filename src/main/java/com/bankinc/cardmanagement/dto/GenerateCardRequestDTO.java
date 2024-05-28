package com.bankinc.cardmanagement.dto;

import com.bankinc.cardmanagement.util.constant.ConstantsUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GenerateCardRequestDTO {
    @NotBlank(message = "El nombre no puede ser nulo o vacío")
    private String name;
    @NotBlank(message = "El apellido no puede ser nulo o vacío")
    private String lastname;
    @NotBlank(message = "El ID del producto no puede ser nulo o vacío")
    @Size(min = 6, max = 6, message = ConstantsUtil.CARD_GENERATE_VALIDATE_IDPRODUCT)
    private String idProduct;
}
