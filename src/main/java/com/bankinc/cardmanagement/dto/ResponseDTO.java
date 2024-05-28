package com.bankinc.cardmanagement.dto;

import lombok.Data;

@Data
public class ResponseDTO {
    private int codigo;
    private String descripcion;
    private Object result;

    public ResponseDTO(int codigo, String descripcion, Object result) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.result = result;
    }
}
