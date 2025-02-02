package com.projeto.avapay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.avapay.model.Extrato;
import com.projeto.avapay.model.Transacoes;



@Repository
public interface ExtratoRepository extends JpaRepository<Extrato, Long> {

    // Busca extratos associados a uma transação específica
    List<Extrato> findByTransacao(Transacoes transacao);

    // Busca todos os extratos ordenados por data de emissão em ordem decrescente
    List<Extrato> findAllByOrderByDataEmissaoDesc();

    // Busca extratos associados a transações de uma conta específica
    List<Extrato> findByTransacaoContaOrigemIdOrTransacaoContaDestinoId(Long contaOrigemId, Long contaDestinoId);

 // Método correto para buscar extratos baseados em múltiplas transações
    List<Extrato> findByTransacaoIn(List<Transacoes> transacoes);
}