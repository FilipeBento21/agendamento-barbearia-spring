package com.filipe_bento.agendamento_barbearia.service;

import com.filipe_bento.agendamento_barbearia.dto.cliente.ClienteRequestDTO;
import com.filipe_bento.agendamento_barbearia.dto.cliente.ClienteResponseDTO;
import com.filipe_bento.agendamento_barbearia.entity.Cliente;
import com.filipe_bento.agendamento_barbearia.exception.ResourceNotFoundException;
import com.filipe_bento.agendamento_barbearia.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(ClienteResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente não encontrado com o ID: " + id));

        return ClienteResponseDTO.fromEntity(cliente);
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(ClienteResponseDTO::fromEntity)
                .toList();
    }

    @Transactional
    public ClienteResponseDTO salvar(ClienteRequestDTO dto) {

        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setTelefone(dto.telefone());
        cliente.setEmail(dto.email());

        Cliente salvo = repository.save(cliente);

        return ClienteResponseDTO.fromEntity(salvo);
    }

    @Transactional
    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto) {

        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente não encontrado com o ID: " + id));

        if (dto.nome() != null)
            cliente.setNome(dto.nome());

        if (dto.telefone() != null)
            cliente.setTelefone(dto.telefone());

        if (dto.email() != null)
            cliente.setEmail(dto.email());

        Cliente atualizado = repository.save(cliente);

        return ClienteResponseDTO.fromEntity(atualizado);
    }

    @Transactional
    public void deletar(Long id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente não encontrado com o ID: " + id));

        repository.delete(cliente);
    }
}