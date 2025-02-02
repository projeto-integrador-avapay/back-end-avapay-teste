package com.projeto.avapay.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.avapay.model.Conta;
import com.projeto.avapay.model.Pix;


@Repository
public interface PixRepository extends JpaRepository<Pix, Long> {

    // Busca todos os Pix enviados por uma conta de origem
    List<Pix> findByContaOrigem(Conta contaOrigem);

    // Busca todos os Pix recebidos por uma conta de destino
    List<Pix> findByContaDestino(Conta contaDestino);

    // Busca todos os Pix ordenados por data e hora em ordem decrescente
    List<Pix> findAllByOrderByDataHoraDesc();

    // Busca Pix por intervalo de data e hora
    List<Pix> findByDataHoraBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Verifica se existe uma chave Pix cadastrada para a conta destino
    boolean existsByContaDestino(Conta contaDestino);
}