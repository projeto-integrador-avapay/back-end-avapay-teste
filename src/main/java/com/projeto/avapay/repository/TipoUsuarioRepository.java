package com.projeto.avapay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.avapay.model.TipoUsuario;


@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Long> {

    // Busca um TipoUsuario pelo nome do tipo
    TipoUsuario findByTipoUsuario(String tipoUsuario);

    // Verifica se existe um TipoUsuario com um nome específico
    boolean existsByTipoUsuario(String tipoUsuario);
}