package com.projeto.avapay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.avapay.model.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    // Busca uma conta pelo número
    Optional<Conta> findByNumeroConta(String numeroConta);

    // Verifica se existe uma conta com um número específico
    boolean existsByNumeroConta(String numeroConta);

    // Busca uma conta por agência
    Optional<Conta> findByAgencia(Integer agencia);

    // Verifica se existe uma conta com uma agência específica
    boolean existsByAgencia(Integer agencia);

}