package com.projeto.avapay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.avapay.model.Extrato;
import com.projeto.avapay.service.ExtratoService;



@RestController
@RequestMapping("/extratos")
public class ExtratoController {

    @Autowired
    private ExtratoService extratoService;

    @GetMapping("/{contaId}")
    public ResponseEntity<?> listarExtratosPorConta(@PathVariable Long contaId) {
        List<Extrato> extratos = extratoService.listarExtratosPorConta(contaId);

        if (extratos.isEmpty()) {
            return ResponseEntity.status(404).body("Nenhum extrato encontrado para esta conta.");
        }

        return ResponseEntity.ok(extratos);
    }
}