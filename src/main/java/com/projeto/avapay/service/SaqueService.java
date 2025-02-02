package com.projeto.avapay.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.avapay.model.Conta;
import com.projeto.avapay.model.Saque;
import com.projeto.avapay.repository.ContaRepository;
import com.projeto.avapay.repository.SaqueRepository;


@Service
public class SaqueService {

    @Autowired
    private SaqueRepository saqueRepository;

    @Autowired
    private ContaRepository contaRepository;

    private static final BigDecimal LIMITE_SAQUE = new BigDecimal("5000.00");
    private static final BigDecimal TAXA_SAQUE = new BigDecimal("5.00");

    // Realiza um saque em uma conta específica
    @Transactional
    public Saque realizarSaque(Long contaId, BigDecimal valor, String tipoTransacao) {
        Conta conta = contaRepository.findById(contaId).orElseThrow(
            () -> new SaqueContaNaoEncontradaException("Conta não encontrada para saque."));

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser maior que zero.");
        }

        if (valor.compareTo(LIMITE_SAQUE) > 0) {
            throw new SaqueLimiteExcedidoException("O valor do saque excede o limite permitido de R$ 5.000,00.");
        }

        // Valor total do saque incluindo a taxa
        BigDecimal valorTotal = valor.add(TAXA_SAQUE);

        if (conta.getSaldo().compareTo(valorTotal) < 0) {
            throw new SaqueSaldoInsuficienteException("Saldo insuficiente para realizar o saque (incluindo taxa).");
        }

        // Atualiza o saldo da conta, subtraindo o valor do saque + taxa
        conta.setSaldo(conta.getSaldo().subtract(valorTotal));
        contaRepository.save(conta);

        // Cria e salva o saque
        Saque saque = new Saque();
        saque.setConta(conta);
        saque.setValor(valor);
        saque.setTipoTransacao(tipoTransacao);
        saque.setDataHora(LocalDateTime.now());

        return saqueRepository.save(saque);
    }
}

// Exceções Personalizadas
class SaqueContaNaoEncontradaException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public SaqueContaNaoEncontradaException(String message) {
        super(message);
    }
}

class SaqueSaldoInsuficienteException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public SaqueSaldoInsuficienteException(String message) {
        super(message);
    }
}

class SaqueLimiteExcedidoException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public SaqueLimiteExcedidoException(String message) {
        super(message);
    }
}