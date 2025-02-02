package com.projeto.avapay.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.avapay.model.Conta;
import com.projeto.avapay.repository.ContaRepository;



@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    // Cria uma nova conta
    @Transactional
    public Conta criarConta(Conta conta) {
        if (contaRepository.existsByNumeroConta(conta.getNumeroConta())) {
            throw new ContaJaExisteException("Já existe uma conta com este número.");
        }
        return contaRepository.save(conta);
    }

    // Busca uma conta pelo ID
    public Optional<Conta> buscarPorId(Long id) {
        return contaRepository.findById(id);
    }

    // Busca uma conta pelo número
    public Optional<Conta> buscarPorNumeroConta(String numeroConta) {
        return contaRepository.findByNumeroConta(numeroConta);
    }

    
 // Atualiza o saldo de uma conta
    @Transactional
    public Conta atualizarSaldo(Long id, BigDecimal novoSaldo) {
        Conta contaExistente = contaRepository.findById(id)
            .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada para atualização de saldo."));

        if (novoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new SaldoInvalidoException("O saldo não pode ser negativo.");
        }

        contaExistente.setSaldo(novoSaldo);
        return contaRepository.save(contaExistente);
    }
 // Realizar saque
    @Transactional
    public void realizarSaque(Long idConta, BigDecimal valor) {
        Conta conta = contaRepository.findById(idConta)
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada."));

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new SaldoInvalidoException("O valor do saque deve ser maior que zero.");
        }

        if (conta.getSaldo().compareTo(valor) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar o saque.");
        }

        conta.setSaldo(conta.getSaldo().subtract(valor));
        contaRepository.save(conta);
    }

    // Remove uma conta pelo ID
    @Transactional
    public void removerConta(Long id) {
        if (!contaRepository.existsById(id)) {
            throw new ContaNaoEncontradaException("Conta não encontrada para exclusão.");
        }
        contaRepository.deleteById(id);
    }

 // Realizar transferência
    @Transactional
    public void realizarTransferencia(Long idContaOrigem, Long idContaDestino, BigDecimal valor) {
        Conta contaOrigem = contaRepository.findById(idContaOrigem)
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta de origem não encontrada."));
        Conta contaDestino = contaRepository.findById(idContaDestino)
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta de destino não encontrada."));

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new SaldoInvalidoException("O valor da transferência deve ser maior que zero.");
        }

        if (contaOrigem.getSaldo().compareTo(valor) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente na conta de origem.");
        }

        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valor));
        contaDestino.setSaldo(contaDestino.getSaldo().add(valor));

        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);
    }




// Realizar depósito
@Transactional
public void realizarDeposito(Long idConta, BigDecimal valor) {
    Conta conta = contaRepository.findById(idConta)
    	.orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada."));

    if (valor.compareTo(BigDecimal.ZERO) <= 0) {
        throw new SaldoInvalidoException("O valor do depósito deve ser maior que zero.");
    }

    conta.setSaldo(conta.getSaldo().add(valor));
    contaRepository.save(conta);
}

// Exceções Personalizadas
class ContaNaoEncontradaException extends RuntimeException {
	private static final long serialVersionUID = 1L;
    public ContaNaoEncontradaException(String message) {
        super(message);
    }
}

class ContaJaExisteException extends RuntimeException {
	private static final long serialVersionUID = 1L;
    public ContaJaExisteException(String message) {
        super(message);
    }
}

class SaldoInsuficienteException extends RuntimeException {
	private static final long serialVersionUID = 1L;
    public SaldoInsuficienteException(String message) {
        super(message);
    }
}

class SaldoInvalidoException extends RuntimeException {
	private static final long serialVersionUID = 1L;
    public SaldoInvalidoException(String message) {
        super(message);
    }
}}