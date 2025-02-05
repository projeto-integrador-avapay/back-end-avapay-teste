package com.projeto.avapay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.avapay.model.Conta;
import com.projeto.avapay.model.Extrato;
import com.projeto.avapay.model.Transacoes;
import com.projeto.avapay.repository.ExtratoRepository;
import com.projeto.avapay.repository.TransacoesRepository;



@Service
public class ExtratoService {

    @Autowired
    private ExtratoRepository extratoRepository;

    @Autowired
    private TransacoesRepository transacoesRepository;

    // Lista extratos associados a uma conta específica
    public List<Extrato> listarExtratosPorConta(Conta contaId) {
        List<Transacoes> transacoes = transacoesRepository.findByContaOrigemIdOrContaDestinoId(contaId , contaId);
        return extratoRepository.findByTransacaoIn(transacoes);
    }

    // Gera um extrato baseado em uma transação
    @Transactional
    public Extrato gerarExtratoPorTransacao(Long transacaoId) {
        Transacoes transacao = transacoesRepository.findById(transacaoId)
                .orElseThrow(() -> new TransacaoNaoEncontradaException("Transação não encontrada."));

        Extrato extrato = new Extrato();
        extrato.setTransacao(transacao);
        extrato.setTipoTransacao(transacao.getDescricao());
        extrato.setDataEmissao(transacao.getDataHora());

        return extratoRepository.save(extrato);
    }

    // Lista todos os extratos ordenados por data de emissão
    public List<Extrato> listarTodosExtratos() {
        return extratoRepository.findAllByOrderByDataEmissaoDesc();
    }

    // Lista os extratos associados a uma transação específica
    public List<Extrato> listarExtratosPorTransacao(Long transacaoId) {
        Transacoes transacao = transacoesRepository.findById(transacaoId)
                .orElseThrow(() -> new TransacaoNaoEncontradaException("Transação não encontrada."));
        return extratoRepository.findByTransacao(transacao);
    }

    // Busca transações associadas a uma conta específica
    public List<Transacoes> buscarTransacoesPorConta(Conta contaId) {
        return transacoesRepository.findByContaOrigemIdOrContaDestinoId(contaId, contaId);
    }
}

// Exceção Personalizada
class TransacaoNaoEncontradaException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public TransacaoNaoEncontradaException(String message) {
        super(message);
    }
}