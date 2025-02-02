package com.projeto.avapay.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.avapay.model.Conta;
import com.projeto.avapay.model.Pix;
import com.projeto.avapay.repository.ContaRepository;
import com.projeto.avapay.repository.PixRepository;


@Service
public class PixService {

    @Autowired
    private PixRepository pixRepository;

    @Autowired
    private ContaRepository contaRepository;

    // Realiza uma transferência Pix
    @Transactional
    public Pix realizarPix(Long contaOrigemId, Long contaDestinoId, BigDecimal valor, String tipoTransacao) {
        Conta contaOrigem = contaRepository.findById(contaOrigemId)
                .orElseThrow(() -> new PixContaNaoEncontradaException("Conta de origem não encontrada."));
        Conta contaDestino = contaRepository.findById(contaDestinoId)
                .orElseThrow(() -> new PixContaNaoEncontradaException("Conta de destino não encontrada."));

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transferência Pix deve ser maior que zero.");
        }

        if (contaOrigem.getSaldo().compareTo(valor) < 0) {
            throw new PixSaldoInsuficienteException("Saldo insuficiente na conta de origem.");
        }

        if (contaOrigemId.equals(contaDestinoId)) {
            throw new IllegalArgumentException("Não é possível fazer um Pix para a própria conta.");
        }

        validarChavePix(contaDestino);

        // Atualiza os saldos das contas
        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valor));
        contaDestino.setSaldo(contaDestino.getSaldo().add(valor));
        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);

        // Cria e salva a transação Pix
        Pix pix = new Pix();
        pix.setContaOrigem(contaOrigem);
        pix.setContaDestino(contaDestino);
        pix.setValor(valor);
        pix.setTipoTransacao(tipoTransacao);
        pix.setDataHora(LocalDateTime.now());

        return pixRepository.save(pix);
    }

    // Valida se a conta destino tem uma chave Pix cadastrada
    private void validarChavePix(Conta contaDestino) {
        if (!pixRepository.existsByContaDestino(contaDestino)) {
            throw new IllegalArgumentException("Conta de destino não possui chave Pix cadastrada.");
        }
    }
}

// Exceções Personalizadas
class PixContaNaoEncontradaException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public PixContaNaoEncontradaException(String message) {
        super(message);
    }
}

class PixSaldoInsuficienteException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public PixSaldoInsuficienteException(String message) {
        super(message);
    }
}
