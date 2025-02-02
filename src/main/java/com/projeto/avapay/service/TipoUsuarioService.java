package com.projeto.avapay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.avapay.model.TipoUsuario;
import com.projeto.avapay.repository.TipoUsuarioRepository;



@Service
public class TipoUsuarioService {

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    // Cria um novo tipo de usuário
    public TipoUsuario criarTipoUsuario(TipoUsuario tipoUsuario) {
        if (tipoUsuarioRepository.existsByTipoUsuario(tipoUsuario.getTipoUsuario())) {
            throw new IllegalArgumentException("Já existe um tipo de usuário com este nome.");
        }
        return tipoUsuarioRepository.save(tipoUsuario);
    }

    // Busca todos os tipos de usuário
    public List<TipoUsuario> listarTodos() {
        return tipoUsuarioRepository.findAll();
    }

    // Busca um tipo de usuário pelo ID
    public TipoUsuario buscarPorId(Long id) {
        return tipoUsuarioRepository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Tipo de usuário não encontrado."));
    }

    // Remove um tipo de usuário por ID
    public void removerTipoUsuario(Long id) {
        if (!tipoUsuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Tipo de usuário não encontrado para exclusão.");
        }
        tipoUsuarioRepository.deleteById(id);
    }
}