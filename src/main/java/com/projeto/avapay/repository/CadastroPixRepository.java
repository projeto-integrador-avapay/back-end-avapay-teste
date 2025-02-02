package com.projeto.avapay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.avapay.model.CadastroPix;


@Repository
public interface CadastroPixRepository extends JpaRepository<CadastroPix, Long> {

    // Busca um CadastroPix pela chave cadastrada
    CadastroPix findByChaveCadastrada(String chaveCadastrada);

    // Verifica se existe um CadastroPix com uma chave cadastrada específica
    boolean existsByChaveCadastrada(String chaveCadastrada);

    // Busca um CadastroPix pela chave Pix
    CadastroPix findByChavePix(String chavePix);

    // Verifica se existe um CadastroPix com uma chave Pix específica
    boolean existsByChavePix(String chavePix);
}