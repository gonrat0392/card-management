package com.bankinc.cardmanagement.dto;

import com.bankinc.cardmanagement.util.constant.ConstantsUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class GenerateCardRequestDTO {
    @NotBlank(message = "El nombre no puede ser nulo o vacío")
    private String name;
    @NotBlank(message = "El apellido no puede ser nulo o vacío")
    private String lastname;
    @NotBlank(message = "El ID del producto no puede ser nulo o vacío")
    @Size(min = 6, max = 6, message = ConstantsUtil.CARD_GENERATE_VALIDATE_IDPRODUCT)
    private String idProduct;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }
}
