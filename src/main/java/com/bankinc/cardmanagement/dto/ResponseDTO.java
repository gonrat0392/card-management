package com.bankinc.cardmanagement.dto;

public class ResponseDTO {
    private int codigo;
    private String descripcion;
    private Object result;

    public ResponseDTO(int codigo, String descripcion, Object result) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.result = result;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
