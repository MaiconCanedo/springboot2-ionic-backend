package com.nelioalves.cursomc.services;

import ch.qos.logback.core.net.server.Client;
import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.domain.Endereco;
import com.nelioalves.cursomc.domain.enums.Perfil;
import com.nelioalves.cursomc.dto.ClienteDTO;
import com.nelioalves.cursomc.dto.ClienteNewDTO;
import com.nelioalves.cursomc.repositories.ClienteRepository;
import com.nelioalves.cursomc.repositories.EnderecoRepository;
import com.nelioalves.cursomc.security.UserSS;
import com.nelioalves.cursomc.services.exceptions.AuthorizationException;
import com.nelioalves.cursomc.services.exceptions.DataIntegrityException;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Cliente find(Integer id) {
        UserSS user = UserService.authenticated();
        if (user == null || !user.hasHole(Perfil.ADMIN) && !id.equals(user.getId())) throw new AuthorizationException("Acesso Negado");
        Optional<Cliente> cliente = repository.findById(id);
        return cliente.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    public List<ClienteDTO> findAll() {
        List<Cliente> clientes = repository.findAll();
        if (clientes.isEmpty()) throw new ObjectNotFoundException("Nenhum objeto foi encontrado! Tipo: " + Cliente.class.getName());
        return clientes.stream().map(cliente -> new ClienteDTO(cliente)).collect(Collectors.toList());
    }

    public Cliente findByEmail(String email) {
        UserSS user = UserService.authenticated();
        if (user == null || !user.hasHole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
            throw new AuthorizationException("Acesso Negado");
        }

        Cliente cliente = repository.findByEmail(email);
        if (cliente == null) {
            throw new AuthorizationException("Objeto não encontrado! id: " + user.getId() + ", Tipo: " + Cliente.class.getName());
        }
        return cliente;
    }

    public Page<ClienteDTO> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<Cliente> clientes = repository.findAll(pageRequest);
        if (clientes.getContent().isEmpty()) throw new ObjectNotFoundException("Nenhum objeto foi encontrado! Tipo: " + Cliente.class.getName());
        return clientes.map(cliente -> new ClienteDTO(cliente));
    }

    @Transactional
    public Cliente insert(Cliente cliente) {
        cliente.setId(null);
        cliente = repository.save(cliente);
        enderecoRepository.saveAll(cliente.getEnderecos());
        return cliente;
    }

    public Cliente update(Cliente cliente) {
        Cliente newCliente = find(cliente.getId());
        updateData(newCliente, cliente);
        return repository.save(newCliente);
    }

    public void delete(Integer id) {
        find(id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir uma cliente que possui pedidos! ");
        }
    }

    public Cliente fromDTO(ClienteDTO clienteDTO) {
        return new Cliente(clienteDTO);
    }

    public Cliente fromDTO(ClienteNewDTO clienteNewDTO) {
        clienteNewDTO.setSenha(passwordEncoder.encode(clienteNewDTO.getSenha()));
        Cliente cliente = new Cliente(clienteNewDTO);
        Endereco endereco = new Endereco(clienteNewDTO, cliente);
        cliente.getEnderecos().add(endereco);
        cliente.getTelefones().add(clienteNewDTO.getTelefone1());
        if (clienteNewDTO.getTelefone2() != null) cliente.getTelefones().add(clienteNewDTO.getTelefone2());
        if (clienteNewDTO.getTelefone3() != null) cliente.getTelefones().add(clienteNewDTO.getTelefone3());
        return cliente;
    }

    private void updateData(Cliente newCliente, Cliente cliente) {
        newCliente.setNome(cliente.getNome());
        newCliente.setEmail(cliente.getEmail());
    }
}