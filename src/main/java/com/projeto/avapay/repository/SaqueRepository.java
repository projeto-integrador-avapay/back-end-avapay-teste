package com.projeto.avapay.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.avapay.model.Conta;
import com.projeto.avapay.model.Saque;



@Repository
public interface SaqueRepository extends JpaRepository<Saque, Long> {

    // Busca todos os saques realizados por uma conta específica
    List<Saque> findByConta(Conta conta);

    // Busca todos os saques ordenados por data e hora em ordem decrescente
    List<Saque> findAllByOrderByDataHoraDesc();

    // Busca saques por intervalo de valor
    List<Saque> findByTaxaBetween(Double minTaxa, Double maxTaxa);

    // Busca saques realizados dentro de um intervalo de data e hora
    List<Saque> findByDataHoraBetween(LocalDateTime startDate, LocalDateTime endDate);
}