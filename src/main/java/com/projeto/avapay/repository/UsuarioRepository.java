package com.projeto.avapay.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.avapay.model.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Busca um usuário pelo CPF
    Usuario findByCpf(String cpf);

    // Verifica se existe um usuário com um CPF específico
    boolean existsByCpf(String cpf);

    // Busca um usuário pelo email
    Optional <Usuario> findByEmail(String email);

    // Verifica se existe um usuário com um email específico
    boolean existsByEmail(String email);

    // Busca usuários por tipo de usuário
    List<Usuario> findByTipoUsuarioTipoUsuario(String tipoUsuario);


}