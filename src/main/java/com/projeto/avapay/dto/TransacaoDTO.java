package com.projeto.avapay.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransacaoDTO {
    private String tipoTransacao;
    private BigDecimal valor;
    private LocalDateTime dataHora;
    private String contaOrigem;
    private String contaDestino;

    public TransacaoDTO(String tipoTransacao, BigDecimal valor, LocalDateTime dataHora, String contaOrigem, String contaDestino) {
        this.tipoTransacao = tipoTransacao;
        this.valor = valor;
        this.dataHora = dataHora;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
    }

    // Getters
    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getContaOrigem() {
        return contaOrigem;
    }

    public String getContaDestino() {
        return contaDestino;
    }
}