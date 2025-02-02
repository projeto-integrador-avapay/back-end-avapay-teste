package com.projeto.avapay.controller;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.avapay.model.CadastroPix;
import com.projeto.avapay.service.CadastroPixService;



@RestController
@RequestMapping("/pix")
public class CadastroPixController {

    @Autowired
    private CadastroPixService cadastroPixService;

    @PostMapping
    public ResponseEntity<CadastroPix> cadastrarPix(@RequestBody CadastroPix cadastroPix) {
        CadastroPix novoCadastro = cadastroPixService.criarCadastroPix(cadastroPix);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCadastro);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<CadastroPix> cadastroPix = cadastroPixService.buscarPorId(id);

        if (cadastroPix.isPresent()) {
            return ResponseEntity.ok(cadastroPix.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cadastro Pix não encontrado.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarPix(@PathVariable Long id) {
        try {
            cadastroPixService.removerCadastroPix(id);
            return ResponseEntity.ok("Cadastro Pix removido com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cadastro Pix não encontrado.");
        }
    }
}

