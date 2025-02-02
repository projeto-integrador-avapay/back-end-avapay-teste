package com.projeto.avapay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.avapay.model.Conta;
import com.projeto.avapay.model.Transacoes;



@Repository
public interface TransacoesRepository extends JpaRepository<Transacoes, Long> {

    // Busca todas as transações de uma conta de origem
    List<Transacoes> findByContaOrigem(Conta contaOrigem);

    // Busca todas as transações de uma conta de destino
    List<Transacoes> findByContaDestino(Conta contaDestino);

    // Busca todas as transações ordenadas pela data em ordem decrescente
    List<Transacoes> findAllByOrderByDataHoraDesc();

    // Busca todas as transações relacionadas a uma conta pelo ID
    List<Transacoes> findByContaId(Long contaId);

    // Busca todas as transações relacionadas a uma conta (origem ou destino)
    List<Transacoes> findByContaOrigemIdOrContaDestinoId(Long contaOrigemId, Long contaDestinoId);

    // Busca todas as transações em que a conta participou (seja como origem ou destino)
    List<Transacoes> findByContaOrigemOrContaDestino(Long contaOrigemId, Long contaDestinoId);


    }

