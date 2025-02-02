package com.projeto.avapay.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.avapay.model.CadastroPix;
import com.projeto.avapay.repository.CadastroPixRepository;


@Service
public class CadastroPixService {

    @Autowired
    private CadastroPixRepository cadastroPixRepository;

    // Cria um novo cadastro Pix
    @Transactional
    public CadastroPix criarCadastroPix(CadastroPix cadastroPix) {
        if (cadastroPixRepository.existsByChaveCadastrada(cadastroPix.getChaveCadastrada())) {
            throw new ChavePixJaExisteException("Já existe um cadastro Pix com esta chave cadastrada.");
        }
        if (cadastroPixRepository.existsByChavePix(cadastroPix.getChavePix())) {
            throw new ChavePixJaExisteException("Já existe um cadastro Pix com esta chave Pix.");
        }
        return cadastroPixRepository.save(cadastroPix);
    }

    // Busca um cadastro Pix pelo ID
    public Optional<CadastroPix> buscarPorId(Long id) {
        return cadastroPixRepository.findById(id);
    }

    // Busca um cadastro Pix pela chave cadastrada
    public Optional<CadastroPix> buscarPorChaveCadastrada(String chaveCadastrada) {
        return Optional.ofNullable(cadastroPixRepository.findByChaveCadastrada(chaveCadastrada));
    }

    // Busca um cadastro Pix pela chave Pix
    public Optional<CadastroPix> buscarPorChavePix(String chavePix) {
        return Optional.ofNullable(cadastroPixRepository.findByChavePix(chavePix));
    }

    // Remove um cadastro Pix pelo ID
    @Transactional
    public void removerCadastroPix(Long id) {
        if (!cadastroPixRepository.existsById(id)) {
            throw new ChavePixNaoEncontradaException("Cadastro Pix não encontrado para exclusão.");
        }
        cadastroPixRepository.deleteById(id);
    }
}

// Exceções Personalizadas
class ChavePixNaoEncontradaException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public ChavePixNaoEncontradaException(String message) {
        super(message);
    }
}

class ChavePixJaExisteException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public ChavePixJaExisteException(String message) {
        super(message);
    }
}