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

import com.projeto.avapay.service.ContaService;



@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping("/{id}/saque")
    public ResponseEntity<?> realizarSaque(@PathVariable Long id, @RequestBody BigDecimal valor) {
        try {
            contaService.realizarSaque(id, valor);
            return ResponseEntity.ok("Saque realizado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{id}/deposito")
    public ResponseEntity<?> realizarDeposito(@PathVariable Long id, @RequestBody BigDecimal valor) {
        try {
            contaService.realizarDeposito(id, valor);
            return ResponseEntity.ok("Depósito realizado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/transferencia")
    public ResponseEntity<?> realizarTransferencia(
            @RequestParam Long origemId,
            @RequestParam Long destinoId,
            @RequestParam BigDecimal valor) {
        try {
            contaService.realizarTransferencia(origemId, destinoId, valor);
            return ResponseEntity.ok("Transferência realizada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}