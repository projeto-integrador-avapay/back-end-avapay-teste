package com.projeto.avapay.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.avapay.model.Conta;
import com.projeto.avapay.model.Deposito;
import com.projeto.avapay.repository.ContaRepository;
import com.projeto.avapay.repository.DepositoRepository;


@Service
public class DepositoService {

    @Autowired
    private DepositoRepository depositoRepository;

    @Autowired
    private ContaRepository contaRepository;

    // Realiza um depósito em uma conta específica
    @Transactional
    public Deposito realizarDeposito(Long contaDestinoId, BigDecimal valor, String tipoTransacao) {
        Conta contaDestino = contaRepository.findById(contaDestinoId)
                .orElseThrow(() -> new DepositoContaNaoEncontradaException("Conta de destino não encontrada."));

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DepositoValorInvalidoException("O valor do depósito deve ser maior que zero.");
        }

        // Verifica se um depósito idêntico já foi registrado recentemente
        if (depositoRepository.existsByContaDestinoAndValor(contaDestino, valor)) {
            throw new DepositoValorInvalidoException("Depósito já realizado recentemente.");
        }

        // Atualiza o saldo da conta de destino
        contaDestino.setSaldo(contaDestino.getSaldo().add(valor));
        contaRepository.save(contaDestino);

        // Cria e salva o depósito
        Deposito deposito = new Deposito();
        deposito.setContaDestino(contaDestino);
        deposito.setValor(valor);
        deposito.setTipoTransacao(tipoTransacao);
        deposito.setDataHora(LocalDateTime.now());

        return depositoRepository.save(deposito);
    }
}

// Exceções Personalizadas
class DepositoContaNaoEncontradaException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public DepositoContaNaoEncontradaException(String message) {
        super(message);
    }
}

class DepositoValorInvalidoException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public DepositoValorInvalidoException(String message) {
        super(message);
    }
}