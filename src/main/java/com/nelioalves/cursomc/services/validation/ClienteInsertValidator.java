package com.nelioalves.cursomc.services.validation;

import com.nelioalves.cursomc.domain.enums.TipoCliente;
import com.nelioalves.cursomc.dto.ClienteNewDTO;
import com.nelioalves.cursomc.repositories.ClienteRepository;
import com.nelioalves.cursomc.resources.exceptions.FieldMessage;
import com.nelioalves.cursomc.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    private ClienteRepository repository;

    @Override
    public void initialize(ClienteInsert clienteInsert) {
    }

    @Override
    public boolean isValid(ClienteNewDTO clienteNewDTO, ConstraintValidatorContext context) {
        List<FieldMessage> messages = new ArrayList<>();
        //inclua os testes aqui, inserindo erros na lista
        if (clienteNewDTO.getTipo().equals(TipoCliente.PESSOA_FISICA.getCodigo()) && !BR.isValidCPF(clienteNewDTO.getCpfOuCnpj())) {
            messages.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
        }
        if (clienteNewDTO.getTipo().equals(TipoCliente.PESSOA_JURIDICA.getCodigo()) && !BR.isValidCNPJ(clienteNewDTO.getCpfOuCnpj())) {
            messages.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
        }
        if (repository.findByEmail(clienteNewDTO.getEmail()) != null) {
            messages.add(new FieldMessage("email", "E-mail já existente"));
        }
        for (FieldMessage e : messages) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return messages.isEmpty();
    }
}