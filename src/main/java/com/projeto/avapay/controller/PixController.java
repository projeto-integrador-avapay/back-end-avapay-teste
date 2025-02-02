package com.projeto.avapay.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.avapay.service.PixService;

@RestController
@RequestMapping("/pix")
public class PixController {

    @Autowired
    private PixService pixService;

    @PostMapping("/transferir")
    public ResponseEntity<?> realizarPix(
            @RequestParam Long origemId,
            @RequestParam Long destinoId,
            @RequestParam BigDecimal valor,
            @RequestParam String tipoTransacao) { // Adicionado o terceiro parâmetro
        try {
            pixService.realizarPix(origemId, destinoId, valor, tipoTransacao);
            return ResponseEntity.ok("Pix realizado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao realizar Pix: " + e.getMessage());
        }
    }
}