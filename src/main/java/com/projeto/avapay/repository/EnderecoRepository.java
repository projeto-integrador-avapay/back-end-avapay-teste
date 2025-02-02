package com.projeto.avapay.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.avapay.model.Endereco;


@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    // Busca um endereço pelo CEP
    Endereco findByCep(String cep);

    // Verifica se existe um endereço com um CEP específico
    boolean existsByCep(String cep);

    // Busca todos os endereços vinculados a um usuário
    List<Endereco> findByUsuarioId(Long usuarioId);
}