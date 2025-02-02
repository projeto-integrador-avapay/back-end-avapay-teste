package com.projeto.avapay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.avapay.model.Endereco;
import com.projeto.avapay.repository.EnderecoRepository;


@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    // Cria um novo endereço
    @Transactional
    public Endereco criarEndereco(Endereco endereco) {
        if (!endereco.getCep().matches("\\d{5}-\\d{3}")) {
            throw new IllegalArgumentException("Formato de CEP inválido. Use XXXXX-XXX.");
        }

        if (enderecoRepository.existsByCep(endereco.getCep())) {
            throw new CepJaCadastradoException("Já existe um endereço cadastrado com este CEP.");
        }

        return enderecoRepository.save(endereco);
    }

    // Lista todos os endereços
    public List<Endereco> listarTodos() {
        return enderecoRepository.findAll();
    }

    // Busca um endereço pelo ID
    public Endereco buscarPorId(Long id) {
        return enderecoRepository.findById(id).orElseThrow(
            () -> new EnderecoNaoEncontradoException("Endereço não encontrado."));
    }

    // Atualiza os dados de um endereço existente
    @Transactional
    public Endereco atualizarEndereco(Long id, Endereco enderecoAtualizado) {
        Endereco enderecoExistente = enderecoRepository.findById(id).orElseThrow(
            () -> new EnderecoNaoEncontradoException("Endereço não encontrado para atualização."));

        if (!enderecoAtualizado.getCep().matches("\\d{5}-\\d{3}")) {
            throw new IllegalArgumentException("Formato de CEP inválido. Use XXXXX-XXX.");
        }

        if (enderecoRepository.existsByCep(enderecoAtualizado.getCep())
            && !enderecoExistente.getCep().equals(enderecoAtualizado.getCep())) {
            throw new CepJaCadastradoException("Já existe um endereço cadastrado com esse CEP.");
        }

        enderecoExistente.setRua(enderecoAtualizado.getRua());
        enderecoExistente.setNumero(enderecoAtualizado.getNumero());
        enderecoExistente.setComplemento(enderecoAtualizado.getComplemento());
        enderecoExistente.setCep(enderecoAtualizado.getCep());
        enderecoExistente.setBairro(enderecoAtualizado.getBairro());
        enderecoExistente.setCidade(enderecoAtualizado.getCidade());
        enderecoExistente.setEstado(enderecoAtualizado.getEstado());

        return enderecoRepository.save(enderecoExistente);
    }

    // Remove um endereço por ID
    @Transactional
    public void removerEndereco(Long id) {
        if (!enderecoRepository.existsById(id)) {
            throw new EnderecoNaoEncontradoException("Endereço não encontrado para exclusão.");
        }
        enderecoRepository.deleteById(id);
    }
}

// Exceções Personalizadas
class EnderecoNaoEncontradoException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public EnderecoNaoEncontradoException(String message) {
        super(message);
    }
}

class CepJaCadastradoException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public CepJaCadastradoException(String message) {
        super(message);
    }
}