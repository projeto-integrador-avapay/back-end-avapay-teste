package com.projeto.avapay.controller;


import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.avapay.service.SaqueService;


@RestController
@RequestMapping("/saques")
public class SaqueController {

    @Autowired
    private SaqueService saqueService;

    @PostMapping("/{id}")
    public ResponseEntity<?> realizarSaque(@PathVariable Long id,
                                           @RequestParam String tipoTransacao,
                                           @RequestBody BigDecimal valor) {
        try {
            saqueService.realizarSaque(id, valor, tipoTransacao);
            return ResponseEntity.ok("Saque realizado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao realizar saque: " + e.getMessage());
        }
    }
}