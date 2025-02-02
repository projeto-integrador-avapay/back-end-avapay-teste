package com.projeto.avapay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.avapay.dto.CriarUsuarioDTO;
import com.projeto.avapay.dto.UsuarioDTO;
import com.projeto.avapay.service.UsuarioService;



@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Criar um novo usuário (Apenas ADM pode criar)
    @PostMapping
    public ResponseEntity<?> criarUsuario(
            @RequestBody CriarUsuarioDTO criarUsuarioDTO,
            @RequestHeader("Authorization") String token) {
        try {
            // Extrai o tipo de usuário diretamente do token
            boolean isAdmin = extrairTipoUsuarioDoToken(token).equals("ADM");

            if (!isAdmin) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Permissão negada. Apenas administradores podem criar usuários.");
            }

            UsuarioDTO novoUsuario = usuarioService.criarUsuario(criarUsuarioDTO, isAdmin);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
        } catch (Exception e) { // Tratamos qualquer exceção lançada na Service
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar usuário: " + e.getMessage());
        }
    }

    // Buscar todos os usuários
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.buscarTodosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // Buscar um usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuarioPorId(@PathVariable Long id) {
        try {
            UsuarioDTO usuario = usuarioService.buscarUsuarioPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) { // Captura qualquer erro, incluindo usuário não encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
    }

    // Atualizar um usuário
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Long id, @RequestBody CriarUsuarioDTO usuarioAtualizado) {
        try {
            UsuarioDTO usuario = usuarioService.atualizarUsuario(id, usuarioAtualizado);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) { // Captura qualquer erro
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    // Remover um usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerUsuario(@PathVariable Long id) {
        try {
            usuarioService.removerUsuario(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuário removido com sucesso.");
        } catch (Exception e) { // Captura qualquer erro
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
    }

    // Método para extrair a role do usuário diretamente do token JWT
    private String extrairTipoUsuarioDoToken(String token) {
        try {
            String[] partes = token.split("\\.");
            if (partes.length < 3) {
                throw new IllegalArgumentException("Token inválido.");
            }

            // Decodifica a segunda parte do token (Payload)
            String payload = new String(java.util.Base64.getDecoder().decode(partes[1]));

            // Procura pelo campo "role" no JSON do payload
            if (payload.contains("\"role\":\"ADM\"")) {
                return "ADM";
            } else if (payload.contains("\"role\":\"CLIENTE\"")) {
                return "CLIENTE";
            } else {
                return "DESCONHECIDO";
            }
        } catch (Exception e) {
            return "ERRO";
        }
    }
}