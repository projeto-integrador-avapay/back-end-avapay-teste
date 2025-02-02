package com.projeto.avapay.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.avapay.dto.TransacaoDTO;
import com.projeto.avapay.model.Transacoes;
import com.projeto.avapay.repository.TransacoesRepository;


@Service
public class TransacoesService {

    @Autowired
    private TransacoesRepository transacoesRepository;

    // Método para listar transações por conta e retornar como DTO
    public List<TransacaoDTO> listarTransacoesPorConta(Long contaId) {
        return transacoesRepository.findByContaOrigemOrContaDestino(contaId, contaId).stream()
                .map(transacao -> new TransacaoDTO(
                        transacao.getTipoTransacao(),
                        transacao.getValor(),
                        transacao.getDataHora(),
                        transacao.getContaOrigem().getNumeroConta(),
                        transacao.getContaDestino().getNumeroConta()
                ))
                .collect(Collectors.toList());
    }

    // Método separado para buscar transações por conta sem DTO
    public List<Transacoes> buscarTransacoesPorConta(Long contaId) {
        return transacoesRepository.findByContaOrigemIdOrContaDestinoId(contaId, contaId);
    }
}