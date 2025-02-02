package com.projeto.avapay.repository;





import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.avapay.model.Conta;
import com.projeto.avapay.model.Deposito;

@Repository
public interface DepositoRepository extends JpaRepository<Deposito, Long> {

    // Busca todos os depósitos realizados para uma conta de destino específica
    List<Deposito> findByContaDestino(Conta contaDestino);

    // Busca todos os depósitos realizados por uma conta de origem específica
    List<Deposito> findByContaOrigem(Conta contaOrigem);

    // Busca todos os depósitos ordenados por data e hora em ordem decrescente
    List<Deposito> findAllByOrderByDataHoraDesc();

    // Verifica se já existe um depósito para a conta e valor informados
    boolean existsByContaDestinoAndValor(Conta contaDestino, BigDecimal valor);
}


