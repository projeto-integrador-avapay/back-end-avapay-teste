package com.projeto.avapay.controller;

import java.util.List;

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

import com.projeto.avapay.model.TipoUsuario;
import com.projeto.avapay.service.TipoUsuarioService;





@RestController
@RequestMapping("/tipos-usuario")
public class TipoUsuarioController {

    @Autowired
    private TipoUsuarioService tipoUsuarioService;

    // Criar um novo tipo de usuário
    @PostMapping
    public ResponseEntity<TipoUsuario> criarTipoUsuario(@RequestBody TipoUsuario tipoUsuario) {
        try {
            TipoUsuario novoTipo = tipoUsuarioService.criarTipoUsuario(tipoUsuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoTipo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Listar todos os tipos de usuário
    @GetMapping
    public ResponseEntity<List<TipoUsuario>> listarTodos() {
        List<TipoUsuario> tipos = tipoUsuarioService.listarTodos();
        return ResponseEntity.ok(tipos);
    }

    // Buscar um tipo de usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<TipoUsuario> buscarPorId(@PathVariable Long id) {
        try {
            TipoUsuario tipoUsuario = tipoUsuarioService.buscarPorId(id);
            return ResponseEntity.ok(tipoUsuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Remover um tipo de usuário por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerTipoUsuario(@PathVariable Long id) {
        try {
            tipoUsuarioService.removerTipoUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}