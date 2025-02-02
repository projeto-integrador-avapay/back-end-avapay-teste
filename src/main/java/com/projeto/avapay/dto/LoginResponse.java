package com.projeto.avapay.dto;

public class LoginResponse {
    private String token;
    private String tipoUsuario;

    public LoginResponse(String token, String tipoUsuario) {
        this.token = token;
        this.tipoUsuario = tipoUsuario;
    }

    public String getToken() {
        return token;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }
}