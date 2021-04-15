package com.nelioalves.cursomc.domain.service;

import com.nelioalves.cursomc.domain.entity.Cliente;
import com.nelioalves.cursomc.domain.entity.Endereco;
import com.nelioalves.cursomc.domain.entity.enums.Perfil;
import com.nelioalves.cursomc.api.model.ClienteModel;
import com.nelioalves.cursomc.api.model.input.ClienteInput;
import com.nelioalves.cursomc.domain.repository.ClienteRepository;
import com.nelioalves.cursomc.domain.repository.EnderecoRepository;
import com.nelioalves.cursomc.core.security.UserSS;
import com.nelioalves.cursomc.domain.exception.AuthorizationException;
import com.nelioalves.cursomc.domain.exception.DataIntegrityException;
import com.nelioalves.cursomc.domain.exception.ObjectNotFoundException;
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

    public List<ClienteModel> findAll() {
        List<Cliente> clientes = repository.findAll();
        if (clientes.isEmpty()) throw new ObjectNotFoundException("Nenhum objeto foi encontrado! Tipo: " + Cliente.class.getName());
        return clientes.stream().map(cliente -> new ClienteModel(cliente)).collect(Collectors.toList());
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

    public Page<ClienteModel> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<Cliente> clientes = repository.findAll(pageRequest);
        if (clientes.getContent().isEmpty()) throw new ObjectNotFoundException("Nenhum objeto foi encontrado! Tipo: " + Cliente.class.getName());
        return clientes.map(cliente -> new ClienteModel(cliente));
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

    public Cliente fromDTO(ClienteModel clienteModel) {
        return new Cliente(clienteModel);
    }

    public Cliente fromDTO(ClienteInput clienteInput) {
        clienteInput.setSenha(passwordEncoder.encode(clienteInput.getSenha()));
        Cliente cliente = new Cliente(clienteInput);
        Endereco endereco = new Endereco(clienteInput, cliente);
        cliente.getEnderecos().add(endereco);
        cliente.getTelefones().add(clienteInput.getTelefone1());
        if (clienteInput.getTelefone2() != null) cliente.getTelefones().add(clienteInput.getTelefone2());
        if (clienteInput.getTelefone3() != null) cliente.getTelefones().add(clienteInput.getTelefone3());
        return cliente;
    }

    private void updateData(Cliente newCliente, Cliente cliente) {
        newCliente.setNome(cliente.getNome());
        newCliente.setEmail(cliente.getEmail());
    }
}