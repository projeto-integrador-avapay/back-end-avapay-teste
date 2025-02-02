package com.projeto.avapay.service;



import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.projeto.avapay.dto.CriarUsuarioDTO;
import com.projeto.avapay.dto.UsuarioDTO;
import com.projeto.avapay.model.Usuario;
import com.projeto.avapay.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Criar um novo usuário (Somente para Administradores)
    @Transactional
    public UsuarioDTO criarUsuario(CriarUsuarioDTO criarUsuarioDTO, boolean isAdmin) {
        if (!isAdmin && "ADM".equals(criarUsuarioDTO.getTipoUsuario())) {
            throw new IllegalArgumentException("Apenas administradores podem criar outros administradores.");
        }

        if (usuarioRepository.existsByEmail(criarUsuarioDTO.getEmail())) {
            throw new EmailJaCadastradoException("E-mail já cadastrado.");
        }
        if (usuarioRepository.existsByCpf(criarUsuarioDTO.getCpf())) {
            throw new CpfJaCadastradoException("CPF já cadastrado.");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(criarUsuarioDTO.getNome());
        novoUsuario.setCpf(criarUsuarioDTO.getCpf());
        novoUsuario.setEmail(criarUsuarioDTO.getEmail());
        novoUsuario.setTelefone(criarUsuarioDTO.getTelefone());
        novoUsuario.setEndereco(criarUsuarioDTO.getEndereco());
        novoUsuario.setSenha(passwordEncoder.encode(criarUsuarioDTO.getSenha()));
        novoUsuario.setTipoUsuario("CLIENTE"); // Garantimos que por padrão é CLIENTE

        usuarioRepository.save(novoUsuario);

        return new UsuarioDTO(
            novoUsuario.getId(),
            novoUsuario.getNome(),
            novoUsuario.getEmail(),
            novoUsuario.getTelefone(),
            novoUsuario.getTipoUsuario()
        );
    }

    // Buscar um usuário por ID
    public UsuarioDTO buscarUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado."));
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getTipoUsuario()
        );
    }

    // Atualizar um usuário
    @Transactional
    public UsuarioDTO atualizarUsuario(Long id, CriarUsuarioDTO usuarioAtualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado."));

        usuarioExistente.setNome(usuarioAtualizado.getNome());
        usuarioExistente.setTelefone(usuarioAtualizado.getTelefone());
        usuarioExistente.setEmail(usuarioAtualizado.getEmail());
        usuarioExistente.setEndereco(usuarioAtualizado.getEndereco());

        usuarioRepository.save(usuarioExistente);

        return new UsuarioDTO(
                usuarioExistente.getId(),
                usuarioExistente.getNome(),
                usuarioExistente.getEmail(),
                usuarioExistente.getTelefone(),
                usuarioExistente.getTipoUsuario()
        );
    }

    // Buscar todos os usuários e retornar como DTOs
    public List<UsuarioDTO> buscarTodosUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream()
                .map(usuario -> new UsuarioDTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getTelefone(),
                        usuario.getTipoUsuario()))
                .collect(Collectors.toList());
    }


    // Remover usuário
    @Transactional
    public void removerUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado para exclusão.");
        }
        usuarioRepository.deleteById(id);
    }

    // Exceções personalizadas (Criadas dentro da classe)
    class EmailJaCadastradoException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        public EmailJaCadastradoException(String message) {
            super(message);
        }
    }

    class CpfJaCadastradoException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        public CpfJaCadastradoException(String message) {
            super(message);
        }
    }

    class UsuarioNaoEncontradoException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        public UsuarioNaoEncontradoException(String message) {
            super(message);
        }
    }
}