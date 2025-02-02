package com.projeto.avapay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.avapay.model.Transacoes;
import com.projeto.avapay.service.TransacoesService;

@RestController
@RequestMapping("/transacoes")
public class TransacoesController {

    @Autowired
    private TransacoesService transacoesService;

    @GetMapping("/{contaId}")
    public ResponseEntity<?> buscarTransacoesPorConta(@PathVariable Long contaId) {
        List<Transacoes> transacoes = transacoesService.buscarTransacoesPorConta(contaId);

        if (transacoes.isEmpty()) {
            return ResponseEntity.status(404).body("Nenhuma transação encontrada para esta conta.");
        }

        return ResponseEntity.ok(transacoes);
    }
}